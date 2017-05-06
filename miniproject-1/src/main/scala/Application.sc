package somePackage

import java.nio.file.Paths

import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.actor.{Actor, ActorLogging, ActorSystem, Cancellable, Props}
import akka.serialization.ByteArraySerializer
import akka.stream.ActorMaterializer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer

import scala.concurrent.duration.Duration
import scala.util.Random
import akka.stream.scaladsl.{FileIO, Keep, Sink, Source}
import akka.util.ByteString
import somePackage.Application.system

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.{Duration, _}
import scala.io.Source.fromFile





// Lines Producer
class LinesProducer extends Actor with ActorLogging {
  import LinesProducerCompanion._



  def receive = {
    case Start => {
      log.info("LinesProducer Started.")
      //val ps = ProducerSettings( system, new ByteArraySerializer, new StringSerializer ).withBootStrapServers("192.168.99.100:9092")
      FileIO.fromPath(Paths.get("C:\\Users\\tnkteja\\Documents\\GitHub\\scala-immersion-program\\project\\1000-genomes.csv"))
        .runForeach(println)
      /* .map( line => {new ProducerRecord[Array[Byte],ByteString]("genomes",line)})
      .runWith( Producer.plainSink( ps ) )*/
    }
  }
}


object LinesProducerCompanion {
  val materializer:ActorMaterializer= ActorMaterializer()

  case object Start
}


  implicit val system = ActorSystem("some")
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val LinesProducer = system.actorOf(Props[LinesProducer], "LinesProducer")
  LinesProducer ! LinesProducerCompanion.Start


