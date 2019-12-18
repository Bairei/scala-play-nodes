package pl.com.britenet.models

import org.apache.poi.ss.usermodel.{CellType, Row}

import scala.jdk.CollectionConverters._

class NodeTreeService {

  def buildNodeTree(): List[Node] = {
    val workbookOptional = WorkbookUtils.createWorkbookFromFileName("test1.xlsx")
    val rows = workbookOptional
      .map(workbook => workbook.getSheetAt(0).iterator().asScala.toList)
      .getOrElse(List.empty)

    val nodesList = rows
      .filter((row: Row) => row.getCell(3).getCellType == CellType.NUMERIC)
      .map(row => mapToNode(row))

    NodeMapper.createNodeTree(nodesList)
  }

  private def mapToNode(row: Row): DetailedNode = {
    val id = row.getCell(3).getNumericCellValue.intValue
    row.cellIterator().asScala.toList.zipWithIndex.find {
      case (cell, _) => cell.getCellType == CellType.STRING && cell.getStringCellValue.nonEmpty
    }
      .map(cellWithId => DetailedNode(Node(id, cellWithId._1.getStringCellValue), cellWithId._2))
      .get
  }
}
