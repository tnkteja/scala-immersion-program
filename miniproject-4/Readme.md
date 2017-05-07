#Mini Project 4 - Reading from Kafka Topic Partition and Writing to Elastic Search

This miniproject  is about reading lines from kafka topic partition from earlier miniproject2 and writing it elastic search.

![miniproject1](https://raw.githubusercontent.com/tnkteja/scala-immersion-program/master/miniproject-3/images/miniproject3.png)
## 0.0 - Setup
### 0.1 Running docker
Docker container with kafka, zookeeper, elasticsearch, cassandra, neoo4j is spun and the respective instances are accesses at 192.168.99.100 at respective ports.

## 1.0 Step1 - Running miniproject1, miniproject2 and miniproject3
Change the host address of the kafka server instance from localhost to 192.168.99.100.
```bash
$sbt run
```
##  2.0 start miniproject4

![out](https://raw.githubusercontent.com/tnkteja/scala-immersion-program/master/miniproject-4/images/out.png)

[Watch on screencast](https://www.screencast.com/t/52K9kwOjCw)



## References