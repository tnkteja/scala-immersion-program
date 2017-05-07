package com.miniproject2

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer

object Application extends App {
  implicit val system: ActorSystem = ActorSystem("someActorSystem")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  val linesConsumerAndProducer = system.actorOf(Props(new LinesConsumerAndProducer), "LinesProducer")
  linesConsumerAndProducer ! LinesConsumerAndProducerCompanion.Start
}