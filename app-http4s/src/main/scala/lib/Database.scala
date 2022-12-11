package lib

import doobie._
import doobie.implicits._
import cats._
import cats.effect._
import cats.implicits._
import doobie.util.ExecutionContexts
import cats.effect.unsafe.implicits.global
import cats.effect.unsafe.implicits.global

object Database:
  val xa = Transactor.fromDriverManager[IO](
    "com.mysql.cj.jdbc.Driver",            // driver classname
    "jdbc:mysql://localhost:13306/test_database",   // connect URL (driver-specific)
    "test",                                // user
    "test"                                 // password
  )

  val program1 = 42.pure[ConnectionIO]
  val io1 = program1.transact(xa)
  val res1 = io1.unsafeRunSync()
  println("----------------")
  println(res1)

  val program2 = sql"select 42".query[Int].unique
  val io2 = program2.transact(xa)
  val res2 = io2.unsafeRunSync()
  println("----------------")
  println(res2)

  val program3: ConnectionIO[(Int, Double)] =
    for {
      a <- sql"select 42".query[Int].unique
      b <- sql"select random()".query[Double].unique
    } yield (a, b)
  val res3 = program3.transact(xa).unsafeRunSync()
