package models

import javax.inject.Inject

import com.github.tototoshi.slick.H2JodaSupport._
import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

case class Movie(id: Long, title: String, release: Option[DateTime], country: Option[String], director: Option[String], synopsis: Option[String], imdb: Option[String], tomatoes: Option[String], details: Option[Long])

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

  def findByImdb(imdb: String): Future[List[Movie]] =
    db.run(Movies.filter(_.imdb === imdb).to[List].result)

  def findByTomatoes(tomatoes: String): Future[List[Movie]] =
    db.run(Movies.filter(_.tomatoes === tomatoes).to[List].result)


  private class MoviesTable(tag: Tag) extends Table[Movie](tag, "MOVIE") {

    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)

    def title = column[String]("TITLE")

    def release = column[Option[DateTime]]("RELEASE")

    def country = column[Option[String]]("COUNTRY")

    def director = column[Option[String]]("DIRECTOR")

    def synopsis = column[Option[String]]("SYNOPSIS")

    def imdb = column[Option[String]]("IMDB")

    def tomatoes = column[Option[String]]("TOMATOES")

    def details = column[Option[Long]]("DETAILS")

    def * = (id, title, release, country, director, synopsis, imdb, tomatoes, details) <>(Movie.tupled, Movie.unapply)

  }

}