package com.miniproject1

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer

object Application extends App {

  implicit val system: ActorSystem = ActorSystem("someActorSystem")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  val linesProducer = system.actorOf(Props(new LinesProducer), "LinesProducer")
  linesProducer ! LinesProducerCompanion.Start

}