package typeclasswebinar.e1.hierarchy

// The library
object Phonebook {
  trait ToJson { def toJson: String }

  case class Record(firstName: String, lastName: String, phone: String) extends ToJson {
    def toJson = s"""{"first_name": "$firstName", "last_name": "$lastName", "phone": "$phone"}"""
  }
}
import Phonebook._

// Single record serialization
object E1_RecordsSingle extends App {
  val record = Record("John", "Smith", "0123456789")
  println(record.toJson)
}

// List of records serialization.
// How do we integrate `toJson` behavior with the `List` class
// of the core Scala library?
object E2_RecordsList extends App {
  val records = List(
    Record("John", "Smith"  , "0123456789"),
    Record("Tom" , "Johnson", "9876543210")
  )

  def toJson(lst: List[ToJson]) =
    s"""[
       |  ${lst.map(_.toJson).mkString(",\n  ")}
       |]""".stripMargin

  println(toJson(records))
}
