package scala

import com.comcast.ip4s._
import cats.effect._
import cats.effect.unsafe.IORuntime
import cats.implicits._, org.http4s.implicits._

import org.http4s.dsl.io._
// import org.http4s.implicits._
import org.http4s.HttpRoutes
import org.http4s.server.Router
import org.http4s.ember.server.*

// import org.http4s.twirl._
import play.twirl.api._

object Server extends IOApp, TwirlInstances {

  val hooService = HttpRoutes.of[IO] {
    case GET -> Root / "hoo" / age =>
      Ok(html.Default(name = "hoo", age = age.toInt))
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
}
