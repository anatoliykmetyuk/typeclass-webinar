package typeclasswebinar.e2.typeclass

object Phonebook {
  trait ToJson[T] { def toJson(x: T): String }

  implicit class ToJsonSyntax[T](x: T)(implicit typeclass: ToJson[T])
    { def toJson: String = typeclass.toJson(x) }

  case class Record(firstName: String, lastName: String, phone: String)

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
