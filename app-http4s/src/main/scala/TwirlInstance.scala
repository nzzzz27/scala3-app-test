package scala

import org.http4s.EntityEncoder
import org.http4s.Charset
import org.http4s.Charset.`UTF-8`
import org.http4s.MediaType
import org.http4s.headers.`Content-Type`
import cats.effect.IO
import play.twirl.api._

trait TwirlInstances {

  given htmlContentEncoder(using charset: Charset = `UTF-8`): EntityEncoder[IO, Html] =
    contentEncoder(MediaType.text.html)

  private def contentEncoder[C <: Content](
    mediaType: MediaType
  )(using charset: Charset): EntityEncoder[IO, C] =
    EntityEncoder.stringEncoder
     .contramap[C](content => content.body)
     .withContentType(`Content-Type`(mediaType, charset))
}
