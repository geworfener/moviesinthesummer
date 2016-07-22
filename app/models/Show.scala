package models

import javax.inject.Inject

import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

case class Show(id: Long, movie: Long, location: Long, start: DateTime, end: DateTime)

class ShowRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.driver.api._

  private val Shows = TableQuery[ShowsTable]


  def create(show: Show): Future[Show] =
    db.run(Shows returning Shows += show)


  def all: Future[List[Show]] =
    db.run(Shows.to[List].result)

  def findById(id: Long): Future[Option[Show]] =
    db.run(Shows.filter(_.id === id).result.headOption)

  def findByMovie(movie: Long): Future[List[Show]] =
    db.run(Shows.filter(_.movie === movie).to[List].result)

  def findByLocation(location: Long): Future[List[Show]] =
    db.run(Shows.filter(_.location === location).to[List].result)


  private class ShowsTable(tag: Tag) extends Table[Show](tag, "SHOW") {

    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)

    def movie = column[Long]("MOVIE")

    def location = column[Long]("LOCATION")

    def start = column[DateTime]("START")

    def end = column[DateTime]("END")

    def * = (id, movie, location, start, end) <>(Show.tupled, Show.unapply)

    def ? = (id.?, movie.?, location.?, start.?, end.?).shaped.<>({ r => ; _1.map(_ => Show.tupled((_1.get, _2.get, _3.get, _4.get, _5.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  }

}