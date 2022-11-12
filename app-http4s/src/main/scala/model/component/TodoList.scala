package model.component

case class ViewValueTodoList(
  id:    Long,
  text:  String,
)

object ViewValueTodoList:

  val list = Seq(todo1, todo2)

  val todo1 = ViewValueTodoList(
    id    = 1L,
    text  = "This is TEXT1",
  )
  val todo2 = ViewValueTodoList(
    id    = 2L,
    text  = "This is TEXT2",
  )
