package http

import com.comcast.ip4s.*
import cats.effect.IO

import org.http4s.dsl.io.*
import org.http4s.{ HttpApp, HttpRoutes }
import org.http4s.server.Router
import org.http4s.server.staticcontent.*

import controller.*

object Routes:

  val pokemonController = PokemonController()

  val service = HttpRoutes.of[IO] {
    case GET -> Root =>
      pokemonController.show()
  }

  val router = Router(
    "/"       -> service,
    "/assets" -> fileService(FileService.Config("./assets"))
  ).orNotFound
