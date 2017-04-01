package typeclasswebinar.e2.typeclass

object Phonebook {
  // Type class as a replacement to an interface:
  // a collection of behaviours possible on some data type `T`.
  // Whenever we have a type class `ToJson` for some `T`,
  // we know we can convert it to Json: `typeclass.toJson(target)`.
  trait ToJson[T] { def toJson(x: T): String }

  // Rich wrapper to provide object-oriented syntax:
  // possibility to write `target.toJson` instead of `typeclass.toJson(target)`.
  // This method injection is only possible if it is possible to convert `T`
  // to Json, and this is guaranteed by the typeclass `ToJson[T]` - see its comment above.
  implicit class ToJsonSyntax[T](x: T)(implicit typeclass: ToJson[T])
    { def toJson: String = typeclass.toJson(x) }

  // The data. Two things to notice:
  // 1. No behaviour (methods) is defined on it
  // 2. It is agnostic of the type classes - possible behaviours - you can define on it.
  case class Record(firstName: String, lastName: String, phone: String)

  // A concrete type class for `Record` defines the behaviour in question.
  implicit val recordToJson: ToJson[Record] = new ToJson[Record] {
    def toJson(x: Record): String = s"""{"first_name": "${x.firstName}", "last_name": "${x.firstName}", "phone": "${x.phone}"}"""
  }
}
import Phonebook._

// Single record serialization
object E3_RecordSingle extends App {
  val record = Record("John", "Smith", "0123456789")
  println(record.toJson)
}

// List of records serialization
// Since the data is decoupled from the behaviour and is agnostic of it,
// it is easy to inject the new behaviour for the 3rd party libraries.
// Even for the `List` from core Scala library.
object E4_RecordsList extends App {
  implicit def listToJson[T](implicit typeclass: ToJson[T]): ToJson[List[T]] = new ToJson[List[T]] {
    def toJson(x: List[T]): String =
      s"""[
         |  ${x.map { e => e.toJson }.mkString(",\n  ")}
         |]""".stripMargin
  }

  val records = List(
    Record("John", "Smith"  , "0123456789"),
    Record("Tom" , "Johnson", "9876543210")
  )
  println(records.toJson)
}
