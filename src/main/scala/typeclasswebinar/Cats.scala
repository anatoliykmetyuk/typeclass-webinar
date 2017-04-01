package typeclasswebinar.e3.cats

import cats._
import cats.instances.all._
import cats.syntax.all._

object E9_Cats extends App {
  case class Record[A](firstName: String, lastName: String, phone: A)

  implicit val phoneFunctor: Functor[Record] = new Functor[Record] {
    def map[A, B](r: Record[A])(f: A => B): Record[B] = r.copy(phone = f(r.phone))
  }

  val record = Record("John", "Smith", "0123456789")
  val mapped = record.map { p => s"+1$p" }
  println(mapped)
}
