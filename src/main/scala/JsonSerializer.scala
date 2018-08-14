import akka.serialization._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization._
import org.json4s.{DefaultFormats, Formats}
import ranked.TeamSerializer

case class EventWrapper(manifest: String, payload: String)

class JsonSerializer extends Serializer {
  implicit val formats: Formats = DefaultFormats + TeamSerializer

  override def identifier: Int = 1234

  override def includeManifest: Boolean = false

  override def toBinary(o: AnyRef): Array[Byte] = {
    println(s"Writing: $o")
    write(EventWrapper(o.getClass.getName, write(o))).getBytes()
  }

  override def fromBinary(bytes: Array[Byte], manifest: Option[Class[_]]): AnyRef = {
    val wrapper: EventWrapper = parse(new String(bytes)).extract[EventWrapper]
    implicit val mf = Manifest.classType(Class.forName(wrapper.manifest))
    read(wrapper.payload)
  }
}
