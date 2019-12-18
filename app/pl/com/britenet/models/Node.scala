package pl.com.britenet.models

import play.api.libs.json.{JsArray, JsValue, Json, OWrites, Writes}

case class Node(id: Int = 0, name: String = "", var nodes: List[Node] = List.empty)

object Node {
  implicit val nodeWrites: Writes[Node] = (o: Node) => {
    Json.obj(
      "id" -> o.id,
      "text" -> o.name,
      "children" -> Json.toJson(o.nodes)
    )
  }
}