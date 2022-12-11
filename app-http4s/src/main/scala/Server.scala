package server

import com.comcast.ip4s.*
import cats.effect.{ IO, Resource, ResourceApp }

import org.http4s.{ Response, Status }
import org.http4s.ember.server.EmberServerBuilder

object Server extends ResourceApp.Forever:

  def run(args: List[String]): Resource[IO, Unit] =
    val router = http.Routes.router

    for
      _ <-  EmberServerBuilder
              .default[IO]
              .withHost(ipv4"0.0.0.0")
              .withPort(port"8080")
              .withHttpApp(router)
              .withErrorHandler(error => IO(println{error.getMessage; error}).as(Response(Status.InternalServerError)))
              .build
    yield println("Server started.")
