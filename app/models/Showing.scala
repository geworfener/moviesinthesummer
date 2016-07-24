package models

import javax.inject.Inject

import com.github.tototoshi.slick.H2JodaSupport._
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

case class Showing(id: Long, movie: Long, location: Long, start: DateTime, end: Option[DateTime])

class ShowingRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.driver.api._

  private val Showings = TableQuery[ShowingsTable]


  def create(showing: Showing): Future[Showing] =
    db.run(Showings returning Showings += showing)


  def all: Future[List[Showing]] =
    db.run(Showings.to[List].result)

  def findById(id: Long): Future[Option[Showing]] =
    db.run(Showings.filter(_.id === id).result.headOption)

  def findByMovie(movie: Long): Future[List[Showing]] =
    db.run(Showings.filter(_.movie === movie).to[List].result)

  def findByLocation(location: Long): Future[List[Showing]] =
    db.run(Showings.filter(_.location === location).to[List].result)


  private class ShowingsTable(tag: Tag) extends Table[Showing](tag, "SHOWING") {

    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)

    def movie = column[Long]("MOVIE")

    def location = column[Long]("LOCATION")

    def start = column[DateTime]("START")

    def end = column[Option[DateTime]]("END")

    def * = (id, movie, location, start, end) <>(Showing.tupled, Showing.unapply)

  }

}