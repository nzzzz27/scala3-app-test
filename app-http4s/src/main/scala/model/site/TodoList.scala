package model.site

import model.component.ViewValueTodoList

case class ViewValueSiteTodoList(
  todoList: Seq[ViewValueTodoList],
) extends ViewValueSiteLayout {
  val title = "aaaa"
}
