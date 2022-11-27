package server

import com.comcast.ip4s.*
import cats.effect.*
import cats.effect.unsafe.IORuntime
import cats.implicits.*, org.http4s.implicits.*

import org.http4s.dsl.io.*
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.http4s.ember.server.*
import org.http4s.server.staticcontent.*

import play.twirl.api.*

import model.site.*
import model.component.ViewValueTodo

object Server extends IOApp, TwirlInstances:

  val todoService = HttpRoutes.of[IO] {
    case GET -> Root / "list" / "edit" / id =>
      val vv = ViewValueSiteTodoEdit(
        pageTitle = "Edit Page",
        todo      = ViewValueTodo.findById(id.toLong),
      )
      Ok(site.html.Edit(vv))

    case POST -> Root / "list" / "edit" / id =>
      Ok("Posted !")

    case GET -> Root / "list" =>
      val vv = ViewValueSiteTodoList(
        pageTitle = "List Page",
        todoList = ViewValueTodo.list,
      )
      Ok(site.html.List(vv))
  }

  val barService = HttpRoutes.of[IO] {
    case GET -> Root / "bar" =>
      Ok("OK !!")
  }

  val services = todoService <+> barService
  val httpApp = Router(
    "v1" -> services,
    "assets" -> fileService(FileService.Config("./assets")),
    "" -> todoService
  ).orNotFound

  def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(httpApp)
      .build
      .useForever
      .as(ExitCode.Success)
