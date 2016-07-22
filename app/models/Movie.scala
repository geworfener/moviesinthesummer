package models

import javax.inject.Inject

import org.joda.time.DateTime
import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

case class Movie(id: Long, title: String, release: DateTime, country: String, director: String, synopsis: String, imdb: String, tomatoes: String, details: Long)

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

    def release = column[DateTime]("RELEASE")

    def country = column[String]("COUNTRY")

    def director = column[String]("DIRECTOR")

    def synopsis = column[String]("SYNOPSIS")

    def imdb = column[String]("IMDB")

    def tomatoes = column[String]("TOMATOES")

    def details = column[Long]("DETAILS")

    def * = (id, title, release, country, director, synopsis, imdb, tomatoes, details) <>(Movie.tupled, Movie.unapply)

    def ? = (id.?, title.?, release.?, country.?, director.?, synopsis.?, imdb.?, tomatoes.?, details.?).shaped.<>({ r => ; _1.map(_ => Movie.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  }

}