package com.miniproject3

import akka.actor.{Actor, ActorLogging}
import akka.kafka.ConsumerMessage.CommittableMessage
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, ProducerMessage, ProducerSettings, Subscriptions}
import akka.stream.Materializer
import akka.stream.scaladsl.Sink
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, ByteArraySerializer, StringDeserializer, StringSerializer}

import scala.concurrent.Future

object MyKafkaConsumerForTopicOne {
  type Message = CommittableMessage[Array[Byte], String]
  case object Start
  case object Stop
}

class LinesConsumerAndElasticSearchWriter(implicit mat: Materializer) extends Actor with ActorLogging {

  import LinesConsumerAndElasticSearchWriterCompanion._

  override def preStart(): Unit = {
    super.preStart()
    self ! Start
  }

  override def receive: Receive = {
    case Start => {

      val consumerSettings = ConsumerSettings(context.system, new ByteArrayDeserializer, new StringDeserializer)
        .withBootstrapServers("localhost:9092")
        .withGroupId("genomeFetchers0")
        .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

      val producerSettings = ProducerSettings(context.system, new ByteArraySerializer, new StringSerializer)
        .withBootstrapServers("localhost:9092")

      Consumer.committableSource(consumerSettings, Subscriptions.topics("genomes0"))
        .map { element =>
          log.info(s"genomes0 -> genomes1: $element")
          ProducerMessage.Message(new ProducerRecord[Array[Byte], String](
            "genomes1",
            element.record.value.toLowerCase
          ), element.committableOffset)
        }
        .runWith(Sink.ignore)
      //.runWith(Producer.commitableSink(producerSettings))
    }
  }
}

object LinesConsumerAndElasticSearchWriterCompanion {
  case object Start
}