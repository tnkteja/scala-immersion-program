#Mini Project 2 - Reading from Kafka Topic Partition and writing to another Kafka Topic Partition

This miniproject  is about reading lines from kafka topicc partition from earlier miniproject1 with lines from [1000-genomes.txt]() file and streaming them on to another kafka topic partition.

![miniproject1](https://raw.githubusercontent.com/tnkteja/scala-immersion-program/master/miniproject-2/images/miniproject2.png)
## 0.0 - Setup
### 0.1 Start Zookeeper
Zookeeper is required by all kafka servers, all of which are required to connect with it.
We start Zookeper with default [properties](https://github.com/tnkteja/scala-immersion-program/blob/master/miniproject-1/configurations-used/zookeeper.properties).
![startzookeeper.png](https://github.com/tnkteja/scala-immersion-program/blob/master/miniproject-1/images/startzookeeper.png)
### 0.2 Start kafka server a.k.a broker
with default [properties](https://github.com/tnkteja/scala-immersion-program/blob/master/miniproject-1/configurations-used/server.properties)
![startkafkaserver.png](https://github.com/tnkteja/scala-immersion-program/blob/master/miniproject-1/images/startkafkaserver.png)
##  1.0 Step1 - Create a topic named "genomes0" with one  partition
While the topic can be default created by the Kafka server itself if not exists, we still choose to create a topic ourselves.[1]
```bash
```






## References
1. _https://kafka.apache.org/quickstart_
2. _http://doc.akka.io/docs/akka-stream-kafka/current/producer.html_