import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.TestKit
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}
import ranked._

import scala.util.Random

class GameSpec(_system: ActorSystem) extends TestKit(_system) with FlatSpecLike with Matchers with BeforeAndAfterAll {

  def this() = this(ActorSystem("TestSystem"))

  override def afterAll(): Unit = {
    shutdown(system)
  }


  "A new Game" should "work as intended" in {
    val randomIdentifier = Random.nextInt()
    val game: ActorRef = system.actorOf(Props[Game], s"game-$randomIdentifier")

    game ! StartGame()
    game ! NewGoal(TeamA)
    game ! NewGoal(TeamB)
    game ! NewGoal(TeamB)
    game ! NewGoal(TeamB)
    game ! NewGoal(TeamB)
    game ! NewGoal(TeamB)
    game ! NewGoal(TeamB)
    game ! NewGoal(TeamB)
    game ! NewGoal(TeamB)
    game ! NewGoal(TeamB)
    game ! NewGoal(TeamB)

    Thread.sleep(2000)
  }
}
