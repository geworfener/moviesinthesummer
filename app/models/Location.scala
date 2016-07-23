package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.driver.JdbcProfile

import scala.concurrent.Future

case class Location(id: Long, name: String, cinema: Long, city: String, postal: String, street: String, address: String)

class LocationRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.driver.api._

  private val Locations = TableQuery[LocationsTable]


  def create(location: Location): Future[Location] =
    db.run(Locations returning Locations += location)


  def all: Future[List[Location]] =
    db.run(Locations.to[List].result)

  def findById(id: Long): Future[Option[Location]] =
    db.run(Locations.filter(_.id === id).result.headOption)


  private class LocationsTable(tag: Tag) extends Table[Location](tag, "LOCATION") {

    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)

    def name = column[String]("NAME")

    def cinema = column[Long]("CINEMA")

    def city = column[String]("CITY")

    def postal = column[String]("POSTAL")

    def street = column[String]("STREET")

    def address = column[String]("ADDRESS")


    def * = (id, name, cinema, city, postal, street, address) <>(Location.tupled, Location.unapply)

  }

}