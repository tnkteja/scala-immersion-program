package com.miniproject4

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, Materializer}
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import java.io.IOException

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.math._
import spray.json.{DefaultJsonProtocol, JsArray, JsNumber, JsString, JsValue, RootJsonFormat, deserializationError}

case class Genome(Sample: String, Family_ID: String, Population: String, Population_Description: String, Gender: String, Relationship: String, Unexpected_Parent_Child: String, Non_Paternity: String, Siblings: String, Grandparents: String, Avuncular: String, Half_Siblings: String, Unknown_Second_Order: String, Third_Order: String, In_Low_Coverage_Pilot: String, LC_Pilot_Platforms: String, LC_Pilot_Centers: String, In_High_Coverage_Pilot: String, HC_Pilot_Platforms: String, HC_Pilot_Centers: String, In_Exon_Targetted_Pilot: String, ET_Pilot_Platforms: String)
/*
case class GenomeSection2(ET_Pilot_Centers: String, Has_Sequence_in_Phase1: String, Phase1_LC_Platform: String, Phase1_LC_Centers: String, Phase1_E_Platform: String, Phase1_E_Centers: String, In_Phase1_Integrated_Variant_Set: String, Has_Phase1_chrY_SNPS: String, Has_phase1_chrY_Deletions: String, Has_phase1_chrMT_SNPs: String, Main_project_LC_Centers: String, Main_project_LC_platform: String, Total_LC_Sequence: String, LC_Non_Duplicated_Aligned_Coverage: String, Main_Project_E_Centers: String, Main_Project_E_Platform: String, Total_Exome_Sequence: String, X_Targets_Covered_to_20x_or_greater: String, VerifyBam_E_Omni_Free: String, VerifyBam_E_Affy_Free: String, VerifyBam_E_Omni_Chip: String, VerifyBam_E_Affy_Chip: String)
case class GenomeSection3(VerifyBam_LC_Omni_Free: String, VerifyBam_LC_Affy_Free: String, VerifyBam_LC_Omni_Chip: String, VerifyBam_LC_Affy_Chip: String, LC_Indel_Ratio: String, E_Indel_Ratio: String, LC_Passed_QC: String, E_Passed_QC: String, In_Final_Phase_Variant_Calling: String, Has_Omni_Genotypes: String, Has_Axiom_Genotypes: String, Has_Affy_6_0_Genotypes: String, Has_Exome_LOF_Genotypes: String, EBV_Coverage: String, DNA_Source_from_Coriell: String, Has_Sequence_from_Blood_in_Index: String, Super_Population: String, Super_Population_Description: String)

case class Genome( section1:GenomeSection1, section2: GenomeSection2, section3:GenomeSection3)
*/

trait Protocols extends DefaultJsonProtocol {
  implicit val genomeFormat = jsonFormat22(Genome.apply)
 /* implicit val genomeSection2Format = jsonFormat22(GenomeSection2.apply)
  implicit val genomeSection3Format = jsonFormat18(GenomeSection3.apply)

  implicit val genomeFormat = jsonFormat3(Genome.apply)*/
}

trait Service extends Protocols {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  def config: Config
  val logger: LoggingAdapter

  lazy val elasticsearchQueringFlow: Flow[HttpRequest, HttpResponse, Any] = Http().outgoingConnection("192.168.99.100", 9200)

  val routes = {
    logRequestResult("ElasticSearchQuerier") {
      pathPrefix("genomes") {
        (get & path(Segment)) { id =>
          complete {

            Source.single(RequestBuilding.Get("/miniproject4/genomes/"+id))
              .via(elasticsearchQueringFlow)
              .runWith(Sink.head)
              .flatMap( { response =>
                response.status match {
                  case OK => {
                    println(response.entity)
                    Unmarshal(response.entity).to[String].map(Right(_))
                  }
                  case BadRequest => Future.successful(Left("Bad request to elasticsearch"))
                  case _ => Unmarshal(response.entity).to[String].flatMap { entity =>
                    val error = s"Request to elastic searchh failed with status code ${response.status} and entity $entity"
                    logger.error(error)
                    Future.failed(new IOException(error))
                  }
                }
              })
              .map[ToResponseMarshallable]( {
              case Right(obj) => obj
              case Left(errorMessage) => BadRequest -> errorMessage
            })
          }
        }
      }
    }
  }
}

object Application extends App with Service {
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)

  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))
}
