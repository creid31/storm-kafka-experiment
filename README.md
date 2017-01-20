# Pub/Sub experiment using storm and kafka
This simple project uses Kafka to produce a message and Storm to edit the message to be displayed by a Kafka consumer in the commandline. The storm topology determines if the message is a question or declarative statement and appends question marks or exclamation marks respectively. This project referenced a proof of concept tutorial by Vishnu Viswanath http://vishnuviswanath.com/realtime-storm-kafka1.html.

## Steps to run project
1. Start zookeeper, kafka broker, storm nimbus, supervisor, ui and logviewer. Reference above tutorial for more background information.
2. Confirm that storm is running by navigating to http://localhost:8772/index.html or http://localhost:8080/index.html depending on configurations
3. Clone the project and run `mvn clean install` to generate a jar of the storm topology
4.  Run `storm jar target/storm_kafka-0.0.1-SNAPSHOT.jar com.cecili.storm.Topology`
 ![storm_topology_jar_response](https://cloud.githubusercontent.com/assets/7131553/22166979/d7ff4c06-df31-11e6-883b-32a95dfaf16b.png)
5.  Confirm that the topology was added by referring to the storm UI
![storm_ui_topology_added](https://cloud.githubusercontent.com/assets/7131553/22166970/cef00be6-df31-11e6-9ed0-edfd4ade1a20.png)
6. Create a new kafka topic to work with
`bin/kafka-topics.sh --create --topic test_storm_kafka --zookeeper localhost:2181 --partitions 1 --replication-factor 1
`
7. Start a kafka producer via the command line
`bin/kafka-console-producer.sh --topic test_storm_kafka --broker localhost:9092`
8. Start a kafka consumer via the command line
9. `bin/kafka-console-consumer.sh --topic test_storm_kafka --zookeeper localhost:2181`
10. In another terminal, run `tail -f storm-kafka-topology-test-2-1484947681/6700/worker.log`. The location of the log will depend on configurations. The log name increments based on nimbus restarts and number of previously existing topologies with the same name.
![storm_log_location](https://cloud.githubusercontent.com/assets/7131553/22166961/c52bd356-df31-11e6-974e-decaf1ce8a4b.png)
11. In the kafka producer, type a message and confirm that the kafka consumer received the message
![kafka_producer_msg_sent](https://cloud.githubusercontent.com/assets/7131553/22166956/c1e4cfb8-df31-11e6-9081-4d9e63a9aadd.png)
![kafka_consumer_msg_rcvd](https://cloud.githubusercontent.com/assets/7131553/22166953/bf4d3c68-df31-11e6-9df5-6e1b60342b52.png)
12. Refer to the worker log to view what each bolt is emitting, transferring, and acknowledging
![storm_log_msg_rcvd](https://cloud.githubusercontent.com/assets/7131553/22166952/b937983c-df31-11e6-9e37-60fd0db43680.png)
13. Confirm that the message was correctly counted in the Storm UI by clicking on the name of the topology
![storm_ui_topology_msg_rcvd](https://cloud.githubusercontent.com/assets/7131553/22166968/cbe3e62a-df31-11e6-9422-2a07eddcadf1.png)