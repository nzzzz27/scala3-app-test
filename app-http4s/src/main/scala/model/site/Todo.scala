package model.site

import model.component.ViewValueTodo

case class ViewValueSiteTodoEdit(
  todo: Option[ViewValueTodo],

  val pageTitle: String,
) extends ViewValueSiteLayout
