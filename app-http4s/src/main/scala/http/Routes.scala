package http

import com.comcast.ip4s.*
import cats.effect.IO

import org.http4s.dsl.io.*
import org.http4s.{ HttpApp, HttpRoutes }
import org.http4s.server.Router
import org.http4s.server.staticcontent.*

import controller.*

object Routes:

  val pokemonListController = PokemonListController()

  val service = HttpRoutes.of[IO] {
    case GET -> Root / "list" =>
      pokemonListController.show()
  }

  val router = Router(
    "/"       -> service,
    "/assets" -> fileService(FileService.Config("./assets"))
  ).orNotFound
