package controllers

import javax.inject.Inject

import controllers.Dto._
import models.{CinemaRepo, LocationRepo, MovieRepo, ShowingRepo}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc.{Action, Controller}


class Rest @Inject()(showingRepo: ShowingRepo,
                     movieRepo: MovieRepo,
                     locationRepo: LocationRepo,
                     cinemaRepo: CinemaRepo)
  extends Controller {

  def listShowings = Action.async {
    showingRepo.all.map(show => Ok(Json.toJson(show))
    )
  }

  def listMovies = Action.async {
    movieRepo.all.map(movie => Ok(Json.toJson(movie))
    )
  }

  def listLocations = Action.async {
    locationRepo.all.map(location => Ok(Json.toJson(location))
    )
  }

  def listCinemas = Action.async {
    cinemaRepo.all.map(cinema => Ok(Json.toJson(cinema))
    )
  }

  def getAppVersion = Action {
    Ok(Json.obj(
      "version" -> sbtbuild.Info.version
    ))
  }

}