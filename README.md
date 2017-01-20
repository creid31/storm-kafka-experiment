# Pub/Sub experiment using storm and kafka
This simple project uses Kafka to produce a message and Storm to edit the message to be displayed by a Kafka consumer in the commandline. The storm topology determines if the message is a question or declarative statement and appends question marks or exclamation marks respectively. This project referenced a proof of concept tutorial by Vishnu Viswanath http://vishnuviswanath.com/realtime-storm-kafka1.html.

## Steps to run project
1. Start zookeeper, kafka broker, storm nimbus, supervisor, ui and logviewer. Reference above tutorial for more background information.
2. Confirm that storm is running by navigating to http://localhost:8772/index.html or http://localhost:8080/index.html depending on configurations
3. Clone the project and run `mvn clean install` to generate a jar of the storm topology
4.  Run `storm jar target/storm_kafka-0.0.1-SNAPSHOT.jar com.cecili.storm.Topology`
5.  Confirm that the topology was added by referring to the storm UI
6. Create a new kafka topic to work with
`bin/kafka-topics.sh --create --topic test_storm_kafka --zookeeper localhost:2181 --partitions 1 --replication-factor 1
`
7. Start a kafka producer via the command line
`bin/kafka-console-producer.sh --topic test_storm_kafka --broker localhost:9092`
8. Start a kafka consumer via the command line
9. `bin/kafka-console-consumer.sh --topic test_storm_kafka --zookeeper localhost:2181`
10. In another terminal, run `tail -f storm-kafka-topology-test-2-1484947681/6700/worker.log`. The location of the log will depend on configurations. The log name increments based on nimbus restarts and number of previously existing topologies with the same name.
11. In the kafka producer, type a message and confirm that the kafka consumer received the message
12. Refer to the worker log to view what each bolt is emitting, transferring, and acknowledging
13. Confirm that the message was correctly counted in the Storm UI