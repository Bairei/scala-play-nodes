package pl.com.britenet.controllers

import javax.inject._
import pl.com.britenet.models.NodeTreeService
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents, val nodeTreeService: NodeTreeService) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index: Action[AnyContent] = Action { implicit request =>
    val nodes = nodeTreeService.buildNodeTree()
    Ok(pl.com.britenet.views.html.json.render(nodes))
  }
}
