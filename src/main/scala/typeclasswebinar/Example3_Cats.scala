package typeclasswebinar.e3.cats

// Since data and behaviours on it are agnostic one of another,
// they can be decoupled to different libraries.

// Cats is a library that defines behaviours common to various
// 3rd party data.

import cats._             // Type classes reside in the `cats` package
import cats.syntax.all._  // Syntax wrappers reside there

// A 3rd party library no longer needs to define the type classes and
// the syntax wrappers. It takes them from Cats instead and focuses on the
// data and concrete implementation of the behaviours.
object Phonebook {
  case class Record(firstName: String, lastName: String, phone: String)

  implicit val recordShow: Show[Record] = new Show[Record] {
    def show(x: Record) = s"""{"first_name": "${x.firstName}", "last_name": "${x.firstName}", "phone": "${x.phone}"}"""
  }
}
import Phonebook._

// Single record serialization
object E5_RecordSingle extends App {
  val record = Record("John", "Smith", "0123456789")
  println(record.show)
}

// List of records serialization
object E6_RecordsList extends App {
  implicit def listShow[T](implicit typeclass: Show[T]): Show[List[T]] = new Show[List[T]] {
    def show(x: List[T]): String =
      s"""[
         |  ${x.map { e => e.show }.mkString(",\n  ")}
         |]""".stripMargin
  }

  val records = List(
    Record("John", "Smith"  , "0123456789"),
    Record("Tom" , "Johnson", "9876543210")
  )
  println(records.show)
}
