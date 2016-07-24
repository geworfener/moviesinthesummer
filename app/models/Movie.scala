package models

import javax.inject.Inject

import com.github.tototoshi.slick.H2JodaSupport._
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

case class Movie(id: Long, title: String, released: Option[DateTime], runtime: Option[Long], genre: Option[String], country: Option[String], director: Option[String], synopsis: Option[String], poster: Option[String], imdbid: Option[String], imdbrating: Option[Float], tomatoesid: Option[String], details: Option[Long])

class MovieRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.driver.api._

  private val Movies = TableQuery[MoviesTable]


  def create(movie: Movie): Future[Movie] =
    db.run(Movies returning Movies += movie)


  def all: Future[List[Movie]] =
    db.run(Movies.to[List].result)

  def findById(id: Long): Future[Option[Movie]] =
    db.run(Movies.filter(_.id === id).result.headOption)

  def findByTitle(title: String): Future[List[Movie]] =
    db.run(Movies.filter(_.title === title).to[List].result)

  def findByImdbid(imdbid: String): Future[List[Movie]] =
    db.run(Movies.filter(_.imdbid === imdbid).to[List].result)

  def findByTomatoesid(tomatoesid: String): Future[List[Movie]] =
    db.run(Movies.filter(_.tomatoesid === tomatoesid).to[List].result)


  private class MoviesTable(tag: Tag) extends Table[Movie](tag, "MOVIE") {

    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)

    def title = column[String]("TITLE")

    def released = column[Option[DateTime]]("RELEASED")

    def runtime = column[Option[Long]]("RUNTIME")

    def genre = column[Option[String]]("GENRE")

    def country = column[Option[String]]("COUNTRY")

    def director = column[Option[String]]("DIRECTOR")

    def synopsis = column[Option[String]]("SYNOPSIS")

    def poster = column[Option[String]]("POSTER")

    def imdbid = column[Option[String]]("IMDBID")

    def imdbrating = column[Option[Float]]("IMDBRATING")

    def tomatoesid = column[Option[String]]("TOMATOESID")

    def details = column[Option[Long]]("DETAILS")

    def * = (id, title, released, runtime, genre, country, director, synopsis, poster, imdbid, imdbrating, tomatoesid, details) <>(Movie.tupled, Movie.unapply)

  }

}