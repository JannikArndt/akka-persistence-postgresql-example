package ranked

case class Score(teamA: Int, teamB: Int) {

  override def toString: String = s"Score: Team A = $teamA, Team B = $teamB"

  def For(team: Team): Int = team match {
    case TeamA => teamA
    case TeamB => teamB
  }

  def someoneHasWon: Boolean = (teamA > 9) || (teamB > 9)

  def addGoal(team: Team): Score = team match {
    case TeamA => copy(teamA = this.teamA + 1)
    case TeamB => copy(teamB = this.teamB + 1)
  }

  def removeGoal(team: Team): Score = team match {
    case TeamA => copy(teamA = math.max(this.teamA - 1, 0))
    case TeamB => copy(teamB = math.max(this.teamB - 1, 0))
  }
}

case object Score {
  val empty = Score(0, 0)
}
