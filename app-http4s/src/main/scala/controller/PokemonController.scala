package controller

import cats.effect.IO
import org.http4s.{ Response, Request, Method, Uri }
import org.http4s.dsl.io.*

import org.http4s.client.dsl.io.*
import org.http4s.headers.*
import org.http4s.MediaType
import org.http4s.circe.CirceEntityDecoder.*
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.CirceEntityEncoder.circeEntityEncoder

import org.http4s.Uri
import org.http4s.ember.client.*
import org.http4s.client.*

import model.json.{ JsValuePokemon, JsValuePokemonList }
import model.site.ViewValueSitePokemonList

class PokemonController() extends http.TwirlInstance:
  val request = Request[IO](method = Method.GET).withUri(Uri.unsafeFromString("https://pokeapi.co/api/v2/pokemon?limit=20&offset=0"))

  def show(): IO[Response[IO]] =
    for
      js <- EmberClientBuilder.default[IO].build.use { client =>
        client.expect[JsValuePokemonList](request)
      }
      vv = ViewValueSitePokemonList(list = js)
      res <-     Ok(views.html.site.List(vv))
    yield res
