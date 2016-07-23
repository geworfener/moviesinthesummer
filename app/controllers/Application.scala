package controllers

import javax.inject.Inject

import models.{MovieRepo, ShowRepo}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}

class Application @Inject()(showRepo: ShowRepo, movieRepo: MovieRepo)
                           extends Controller {

  def listShows = Action.async {
    showRepo.all
      .map(shows => Ok(views.html.shows(shows)))
  }


}
