package ranked

sealed trait DomainEvent

case class Goal(team: Team) extends DomainEvent

case class Correction(team: Team) extends DomainEvent
