package com.miniproject3

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer

object Application extends App {

  implicit val system: ActorSystem = ActorSystem("someActorSystem")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  val linesConsumerAndElasticSearchWriter = system.actorOf(Props(new LinesConsumerAndElasticSearchWriter), "LinesProducer")
  linesConsumerAndElasticSearchWriter ! LinesConsumerAndElasticSearchWriterCompanion.Start

}