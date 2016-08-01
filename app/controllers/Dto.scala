package controllers

import play.api.libs.json._

//for now models are dtos
import models._

object Dto {

  implicit val showingFormat = Json.format[Showing]
  implicit val movieFormat = Json.format[Movie]
  implicit val cinemaFormat = Json.format[Cinema]
  implicit val locationFormat = Json.format[Location]

}