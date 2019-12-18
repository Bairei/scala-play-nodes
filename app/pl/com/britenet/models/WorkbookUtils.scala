package pl.com.britenet.models

import org.apache.poi.ss.usermodel.{Workbook, WorkbookFactory}


object WorkbookUtils {
  def createWorkbookFromFileName(fileName: String): Option[Workbook] =
    Option(getClass.getClassLoader.getResourceAsStream(fileName))
      .map(stream => WorkbookFactory.create(stream))
}
