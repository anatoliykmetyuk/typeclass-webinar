package typeclasswebinar.e2.typeclass

object Phonebook {
  object typeclasses {
    trait ToJson[T] { def toJson(x: T): String }
    trait ToXml [T] { def toXml (x: T): String }
    trait ToCSV [T] { def toCSV (x: T): String }
  }

  object data {
    case class Record(firstName: String, lastName: String, phone: String)
  }

  object instances {
    import typeclasses._

    object record extends record
    trait  record {
      import data.Record

      implicit val recordToJson: ToJson[Record] = new ToJson[Record] {
        def toJson(x: Record): String = s"""{"first_name": "${x.firstName}", "last_name": "${x.firstName}", "phone": "${x.phone}"}"""
      }

      implicit val recordToXml: ToXml[Record] = new ToXml[Record] {
        def toXml(x: Record): String = s"""<record><first_name>${x.firstName}</first_name><last_name>${x.firstName}</last_name><phone>${x.phone}</phone></record>"""
      }

      implicit val recordToCSV: ToCSV[Record] = new ToCSV[Record] {
        def toCSV(x: Record): String = s"""${x.firstName},${x.firstName},${x.phone}"""
      }
    }

    object list extends list
    trait list {
      implicit def listToJson[T](implicit typeclass: ToJson[T]): ToJson[List[T]] = new ToJson[List[T]] {
        def toJson(x: List[T]): String =
          s"""[
             |   ${x.map { e => typeclass.toJson(e)}.mkString("\n,  ")}
             |]""".stripMargin
      }
    }

    object all extends record with list
  }

  object syntax {
    import typeclasses._

    implicit class ToToJsonSyntax[T](x: T)(implicit typeclass: ToJson[T])
      { def toJson: String = typeclass.toJson(x) }

    implicit class ToToXmlSyntax [T](x: T)(implicit typeclass: ToXml [T])
      { def toXml : String = typeclass.toXml (x) }

    implicit class ToToCSVSyntax [T](x: T)(implicit typeclass: ToCSV [T])
      { def toCSV : String = typeclass.toCSV (x) }
  }
}

object AddressBook {
  case class Address(firstName: String, lastName: String, address: String)
}

import Phonebook.typeclasses._
import Phonebook.data._
import Phonebook.instances.all._
import Phonebook.syntax._

// Single record serialization
object E5_RecordsSingle extends App {
  val record = Record("John", "Smith", "0123456789")
  println(s"${record.toJson}\n${record.toXml}\n${record.toCSV}")
}

// List of records serialization
object E6_RecordsList extends App {
  val records = List(
    Record("John", "Smith"  , "0123456789"),
    Record("Tom" , "Johnson", "9876543210")
  )
  println(records.toJson)
}

// 3rd party framewrok added: Address book
import AddressBook._

// Type classes for Address
trait AddressTypeclasses {
  implicit val addressToJson: ToJson[Address] = new ToJson[Address] {
    def toJson(x: Address): String =
      s"""{"first_name": "${x.firstName}", "last_name": "${x.firstName}", "address": "${x.address}"}"""
  }  
}

// Single address
object E7_AddressesSingle extends App with AddressTypeclasses {
  val address = Address("John", "Smith", "1600 Pennsylvania Ave NW, Washington, DC 20500, USA")
  println(address.toJson)
}

// List of addresses
object E8_AddressList extends App with AddressTypeclasses {
  val addresses = List(
    Address("John", "Smith", "1600 Pennsylvania Ave NW, Washington, DC 20500, USA"),
    Address("Tom", "Johnson", "Fort Meade, MD 20755-6248")
  )
  println(addresses.toJson)
}
