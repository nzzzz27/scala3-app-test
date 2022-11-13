package model.component

case class ViewValueTodo(
  id:    Long,
  text:  String,
)

object ViewValueTodo:

  def list = Seq(todo1, todo2)

  def findById(id: Long): Option[ViewValueTodo] =
    list.find(_.id == id)

  val todo1 = ViewValueTodo(
    id    = 1L,
    text  = "This is TEXT1",
  )
  val todo2 = ViewValueTodo(
    id    = 2L,
    text  = "This is TEXT2",
  )
