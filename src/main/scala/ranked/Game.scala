package ranked

import akka.persistence.fsm._
import akka.persistence.{SaveSnapshotFailure, SaveSnapshotSuccess}

import scala.reflect._

class Game extends PersistentFSM[State, Score, DomainEvent] {

  override def persistenceId: String = context.self.path.toString

  override def onRecoveryCompleted(): Unit = log.info("Recovery completed!")

  override def domainEventClassTag: ClassTag[DomainEvent] = classTag[DomainEvent]

  startWith(NotStarted, Score.empty)

  when(NotStarted) {
    case Event(StartGame(), _) =>
      log.info("Game started!")
      goto(Running)
    case Event(_, _) =>
      log.error("Please start the game first, using `game ! StartGame()` !")
      stay
  }

  when(Running) {
    case Event(NewGoal(team), score: Score) =>
      log.info(s"Team '$team' scored a goal!")
      if (score.For(team) == 9) {
        log.info(s"Game finished! $team has won with ${score.addGoal(team)}!")
        goto(Finished) applying Goal(team)
      } else
        stay applying Goal(team)

    case Event(RequestCorrection(team), score: Score) =>
      log.info(s"Correcting '$team' scored a goal!")
      if (score.For(team) > 0)
        stay applying Correction(team)
      else
        stay

    case Event(PrintScore(), score: Score) =>
      log.info(s"Score: Team A has ${score.teamA} goals and Team B has ${score.teamB} goals.")
      stay

    case Event(AbortGame(), score: Score) =>
      log.info(s"Aborting game, score was $score")
      goto(Finished)

    case Event(StartGame(), _) =>
      log.error("Game is already started!")
      stay

    case Event(SaveSnapshotSuccess(metadata), data) =>
      log.info(s"Snapshot saved: $metadata with $data")
      stay

    case Event(SaveSnapshotFailure(metadata, cause), data) =>
      log.error(s"Error while saving snapshot $metadata and data $data: $cause")
      stay
  }

  when(Finished) {
    case Event(RequestCorrection(team), currentScore: Score) =>
      log.info(s"Resuming game with score ${currentScore.removeGoal(team)}")
      goto(Running) applying Correction(team)

    case Event(PrintScore(), score: Score) =>
      log.info(s"Score: Team A has ${score.teamA} goals and Team B has ${score.teamB} goals.")
      stay

    case Event(_, _) =>
      log.error("This game is finished. Start a new game to continue!")
      stay
  }

  override def applyEvent(domainEvent: DomainEvent, score: Score): Score = {
    domainEvent match {
      case Goal(team) =>
        score.addGoal(team)
      case Correction(team) =>
        score.removeGoal(team)
    }
  }
}
