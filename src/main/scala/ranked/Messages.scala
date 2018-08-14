package ranked

case class StartGame()
case class NewGoal(team: Team)
case class RequestCorrection(team: Team)
case class AbortGame()
case class PrintScore()