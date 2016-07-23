package controllers

import play.api.libs.json._

//for now models are dtos
import models._

//case class Show(id: Long, movie: Long, location: Long, start: DateTime, end: Option[DateTime])
//case class Movie(id: Long, title: String, release: Option[DateTime], country: Option[String], director: Option[String], synopsis: Option[String], imdb: Option[String], tomatoes: Option[String], details: Option[Long])
//case class Cinema(id: Long, name: String, url: Option[String], tel: Option[String], email: Option[String], location: Option[Long])
//case class Location(id: Long, name: String, cinema: Long, city: String, postal: String, street: String, address: String)

object Dto {

  implicit val showFormat = Json.format[Show]
  implicit val movieFormat = Json.format[Movie]
  implicit val cinemaFormat = Json.format[Cinema]
  implicit val locationFormat = Json.format[Location]

}