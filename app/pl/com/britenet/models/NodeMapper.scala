package pl.com.britenet.models

object NodeMapper {

  def createNodeTree(allNodes: List[DetailedNode]): List[Node] = {
    val rootNodes = allNodes.filter(node => node.depthLevel == 0)
    assignChildren(rootNodes, allNodes, 0)
  }

  private def assignChildren(rootNodes: List[DetailedNode], allNodes: List[DetailedNode], depth: Int): List[Node] =
    rootNodes
      .map(root => Node(root.node.id, root.node.name, assignChildren(findChildren(root, allNodes), allNodes, depth + 1)))

  private def findChildren(root: DetailedNode, allNodes: List[DetailedNode]): List[DetailedNode] =
    findNodeChildCandidates(root, allNodes)
      .filter(node => node.depthLevel == root.depthLevel + 1)

  private def findNodeChildCandidates(root: DetailedNode, allNodes: List[DetailedNode]): List[DetailedNode] = {
    val maybeNextParent = allNodes.find(node => node.depthLevel == root.depthLevel && node.node.id > root.node.id)

    maybeNextParent.map(nextParentNode => allNodes.filter(node => isDepthHigher(root, node) &&
      isAbove(nextParentNode, node) && isBelow(root, node)
    ))
      .getOrElse(allNodes.filter(node => isBelow(root, node) && isDepthHigher(root, node)))
  }

  private def isBelow(thisNode: DetailedNode, otherNode: DetailedNode): Boolean = thisNode.node.id < otherNode.node.id

  private def isDepthHigher(thisNode: DetailedNode, otherNode: DetailedNode): Boolean = thisNode.depthLevel + 1 == otherNode.depthLevel

  private def isAbove(thisNode: DetailedNode, otherNode: DetailedNode): Boolean = thisNode.node.id > otherNode.node.id
}
