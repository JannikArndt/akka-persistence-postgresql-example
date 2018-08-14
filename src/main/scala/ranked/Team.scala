package ranked
import org.json4s.CustomSerializer
import org.json4s.JsonAST.JString

sealed trait Team
case object TeamA extends Team
case object TeamB extends Team

case object TeamSerializer
    extends CustomSerializer[Team](_ ⇒
      ({
        case JString("TeamA") => TeamA
        case JString("TeamB") => TeamB
      }, {
        case msg: Team ⇒ JString(msg.toString)
      }))
