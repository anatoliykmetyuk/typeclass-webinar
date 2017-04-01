package typeclasswebinar.e1.hierarchy

object Phonebook {
  trait ToJson { def toJson: String }
  trait ToXml  { def toXml : String }
  trait ToCSV  { def toCSV : String }

  case class Record(firstName: String, lastName: String, phone: String) extends ToJson with ToXml with ToCSV {
    def toJson = s"""{"first_name": "$firstName", "last_name": "$lastName", "phone": "$phone"}"""
    def toXml  = s"""<record><first_name>$firstName</first_name><last_name>$lastName</last_name><phone>$phone</phone></record>"""
    def toCSV  = s"""$firstName,$lastName,$phone"""
  }

  def toJson(lst: List[ToJson]) =
    s"""[
       |  ${lst.map(_.toJson).mkString(",\n  ")}
       |]""".stripMargin
}

object AddressBook {
  case class Address(firstName: String, lastName: String, address: String)
}

import Phonebook._

// Single record serialization
object E1_RecordsSingle extends App {
  val record = Record("John", "Smith", "0123456789")
  println(s"${record.toJson}\n${record.toXml}\n${record.toCSV}")
}

// List of records serialization
object E2_RecordsList extends App {
  val records = List(
    Record("John", "Smith"  , "0123456789"),
    Record("Tom" , "Johnson", "9876543210")
  )
  println(toJson(records))
}

// 3rd party framewrok added: Address book
import AddressBook._

// Single address serialization
object E3_AddressesSingle extends App {
  val address = Address("John", "Smith", "1600 Pennsylvania Ave NW, Washington, DC 20500, USA")
  // println(address.toJson)
}

// List of addresses
object E4_AddressesList extends App {
  val addresses = List(
    Address("John", "Smith", "1600 Pennsylvania Ave NW, Washington, DC 20500, USA"),
    Address("Tom", "Johnson", "Fort Meade, MD 20755-6248")
  )
  // println(addresses.toJson)
}
