package controllers

import javax.inject.Inject

import models.{MovieRepo, ShowingRepo}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}

class Application @Inject()(showingRepo: ShowingRepo, movieRepo: MovieRepo)
                           extends Controller {

  def listShowings = Action.async {
    showingRepo.all
      .map(showing => Ok(views.html.showings(showing)))
  }


}
