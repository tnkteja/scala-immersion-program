package somePackage

import java.nio.file.Paths

import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.actor.{Actor, ActorLogging, ActorSystem, Cancellable, Props}
import akka.serialization.ByteArraySerializer
import akka.stream.ActorMaterializer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer

import akka.stream.scaladsl.{FileIO, Keep, Sink, Source}
import akka.util.ByteString
import scala.concurrent.Future
import akka.http.scaladsl.model._
import akka.http.scaladsl.Http




// Lines Producer
class LinesProducer extends Actor with ActorLogging {
  import LinesProducerCompanion._

  override def receive = {
    case Start => {
      log.info("LinesProducer Started.")

      FileIO.fromPath(Paths.get("C:\\Users\\tnkteja\\Documents\\GitHub\\scala-immersion-program\\project\\1000-genomes.csv"))
     .map( line => {new ProducerRecord[Array[Byte],ByteString]("genomes",line)})
        .runForeach(println)
      //.runWith( Producer.plainSink( ps ) )
  }
  }
}


object LinesProducerCompanion {
  implicit val system:ActorSystem = ActorSystem("some")
  implicit val materializer:ActorMaterializer= ActorMaterializer()
  implicit val executor = system.dispatcher
  //val ps = ProducerSettings( system, new ByteArraySerializer, new StringSerializer ).withBootStrapServers("192.168.99.100:9092")

  case object Start
}

// LinesConsumerAndElasticSearchWriter
class LinesConsumerAndElasticSearchWriter extends Actor with  ActorLogging {
  import LinesProducerCompanion._

  def receive = {
    case Start => {
      val responseFuture: Future[HttpResponse] =
        Http().singleRequest(HttpRequest(uri = "http://akka.io/"+"scalaimmersionprogram/"+"genomes/"+))
    }
  }
}

object Application extends App {

  val LinesProducer = LinesProducerCompanion.system.actorOf(Props[LinesProducer], "LinesProducer")
  LinesProducer ! LinesProducerCompanion.Start

  // This example app will ping pong 3 times and thereafter terminate the ActorSystem -
  // see counter logic in PingActor
  //system.awaitTermination()
}