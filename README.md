# Akka Persistence with PostgreSQL Journal & Snapshot Example

This example uses 

* [Akka Persistence](https://doc.akka.io/docs/akka/2.5/persistence.html?language=scala) for persistent actors,
* [Persistent FSM](https://doc.akka.io/docs/akka/2.5/persistence.html?language=scala#persistent-fsm) (finite state machine) for states and transitions,
* [akka-persistence-sql-async](https://github.com/okumin/akka-persistence-sql-async) as storage plugin to connect to a PostgreSQL database.

## Getting started

* fire up a PostgreSQL database
* run the `Create Journal and Snapshot.sql` script to create the tables
* make sure the configuration in `src/main/resources/application.conf` match your database
* `sbt test`

## Running in the console

```sbtshell
sbt> console
scala> import akka.actor.{ActorRef, ActorSystem, Props}
scala> import ranked._
scala> val system = ActorSystem("TestSystem")
scala> val game: ActorRef = system.actorOf(Props[Game], "game1")

scala> game ! StartGame
scala> game ! NewGoal(TeamA)
scala> game ! NewGoal(TeamB)

scala> system.terminate()
scala> :quit
sbt> console

scala> import akka.actor.{ActorRef, ActorSystem, Props}
scala> import ranked._
scala> val system = ActorSystem("TestSystem")
scala> val game: ActorRef = system.actorOf(Props[Game], "game1")
[INFO] Recovery completed!

scala> scala> game ! PrintScore()
[INFO] Score: Team A has 2 goals and Team B has 2 goals.

```