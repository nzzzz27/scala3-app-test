package model.json

import io.circe.*
import io.circe.generic.semiauto.*

case class JsValuePokemon(
  name: String,
  url:  String,
)

object JsValuePokemon:
  given Decoder[JsValuePokemon] = deriveDecoder

case class JsValuePokemonList(
  count: Int,
  results: Seq[JsValuePokemon]
)

object JsValuePokemonList:
  given Decoder[JsValuePokemonList] = deriveDecoder
