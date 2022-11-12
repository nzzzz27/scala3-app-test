package server

import com.comcast.ip4s.*
import cats.effect.*
import cats.effect.unsafe.IORuntime
import cats.implicits.*, org.http4s.implicits.*

import org.http4s.dsl.io.*
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.http4s.ember.server.*

import play.twirl.api.*

import model.site.ViewValueSiteTodoList
import model.component.ViewValueTodoList

object Server extends IOApp, TwirlInstances:

  val hooService = HttpRoutes.of[IO] {
    case GET -> Root / "list" =>
      val vv = ViewValueSiteTodoList(
        todoList = ViewValueTodoList.list,
      )
      Ok(site.html.List(vv))
  }

  val barService = HttpRoutes.of[IO] {
    case GET -> Root / "bar" / age =>
      Ok(html.Default(name = "bar", age = age.toInt))
  }

  val services = hooService <+> barService
  val httpApp = Router("/v1" -> hooService, "/v2" -> services).orNotFound

  def run(args: List[String]): IO[ExitCode] =
    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(httpApp)
      .build
      .useForever
      .as(ExitCode.Success)
