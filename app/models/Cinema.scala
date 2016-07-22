package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

case class Cinema(id: Long, name: String, url: String, tel: String, email: String, location: Long)

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

    def url = column[String]("URL")

    def tel = column[String]("TEL")

    def email = column[String]("EMAIL")

    def location = column[Long]("LOCATION")

    def * = (id, name, url, tel, email, location) <>(Cinema.tupled, Cinema.unapply)

    def ? = (id.?, name.?, url.?, tel.?, email.?, location.?).shaped.<>({ r => import r._; _1.map(_ => Cinema.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  }

}