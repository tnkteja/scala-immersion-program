package com.miniproject1

import akka.Done
import akka.actor.{Actor, ActorLogging, Props}
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.Materializer
import akka.stream.scaladsl.Source
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArraySerializer, StringSerializer}


// Futures need execution context to reuse allocated thread pools
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

import scala.io.Source.fromFile


class LinesProducer(implicit mat: Materializer) extends Actor with ActorLogging {
  import LinesProducerCompanion._

  override def preStart(): Unit = {
    super.preStart()
    log.info("Not doing anything in PreStart!")
  }

  override def receive: Receive = {
    case Start => {

      val producerSettings = ProducerSettings(context.system, new ByteArraySerializer, new StringSerializer)
        .withBootstrapServers("192.168.99.100:9092")

      log.info("Initializing writer")

      val kafkaSink = Producer.plainSink(producerSettings)

      //
      val done: Future[Done] = Source.fromIterator(() => fromFile("C:\\Users\\tnkteja\\Documents\\GitHub\\scala-immersion-program\\miniproject-1\\1000-genomes.csv").getLines().drop(1))
        .map(line => {new ProducerRecord[Array[Byte], String]("genomes0", line)})
        .runWith(kafkaSink)

      done.onComplete({
        success =>
          log.info("Writing to kafka Complete!")
          context.stop(self)
      })

      done.onFailure {
        case ex =>
          log.info("*********************Stopping********************")
          context.stop(self)
      }
    }
  }
}

object LinesProducerCompanion{
  val props = Props[LinesProducer]
  case object Start
}