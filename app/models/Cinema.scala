package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

case class Cinema(id: Long, name: String, url: Option[String], tel: Option[String], email: Option[String], location: Option[Long])

class CinemaRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.driver.api._

  private val Cinemas = TableQuery[CinemasTable]


  def create(cinema: Cinema): Future[Cinema] =
    db.run(Cinemas returning Cinemas += cinema)


  def all: Future[List[Cinema]] =
    db.run(Cinemas.to[List].result)

  def findById(id: Long): Future[Option[Cinema]] =
    db.run(Cinemas.filter(_.id === id).result.headOption)

  def findByLocation(location: Long): Future[List[Cinema]] =
    db.run(Cinemas.filter(_.location === location).to[List].result)


  private class CinemasTable(tag: Tag) extends Table[Cinema](tag, "CINEMA") {

    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)

    def name = column[String]("NAME")

    def url = column[Option[String]]("URL")

    def tel = column[Option[String]]("TEL")

    def email = column[Option[String]]("EMAIL")

    def location = column[Option[Long]]("LOCATION")

    def * = (id, name, url, tel, email, location) <>(Cinema.tupled, Cinema.unapply)

  }

}