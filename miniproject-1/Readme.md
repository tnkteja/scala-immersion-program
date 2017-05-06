#Mini Project 1 - Writing to a Kafka Topic Partition

This miniproject  is about reading lines from [1000-genomes.txt]() file and streaming them on to a kafka topic partition.

## 0.0 - Setup
### 0.1 Start Zookeeper
Zookeeper is required by all kafka servers, all of which are required to connect with it.
We start Zookeper with default [properties]().
![startzookeeper.png]()
### 0.2 Start kafka server a.k.a broker
![startkafkaserver.png]()
##  1.0 Step1 - Create a topic named "genomes0" with one  partition
While the topic can be default created by the Kafka server itself if not exists, we still choose to create a topic ourselves.[1]
```bash
```






## References
1. _https://kafka.apache.org/quickstart_