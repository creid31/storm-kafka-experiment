/**
 * Copyright 2016 NCR Corporation
 */
package com.cecili.storm;

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.topology.TopologyBuilder;

import com.cecili.storm.bolt.ExclamationMarkBolt;
import com.cecili.storm.bolt.MasterBolt;
import com.cecili.storm.bolt.QuestionMarkBolt;

/**
 * @author Cecili Reid (cr250220) on Jan 16, 2017
 *
 */
public class Topology {
    public static final String QUESTIONSTREAM = "question-stream";

    public static final String EXCLAIMSTREAM = "declare-stream";
    
    public static final String MASTERBOLT = "MasterBolt_1";

    public static final String QUESTIONBOLT = "QuestionBolt_1";

    public static final String EXCLAIMBOLT = "ExclamationBolt_1";

    SpoutBuilder spoutBuilder;

    public Topology() {
        spoutBuilder = new SpoutBuilder();
    }

    private void submitTopology() throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        KafkaSpout kafkaSpout = spoutBuilder.buildKafkaSpout();
        MasterBolt masterBolt = new MasterBolt();
        QuestionMarkBolt questionBolt = new QuestionMarkBolt();
        ExclamationMarkBolt exclaimBolt = new ExclamationMarkBolt();

        builder.setSpout(SpoutBuilder.SPOUTID, kafkaSpout);

        // First bolt should receive stream from spout
        builder.setBolt(MASTERBOLT, masterBolt).shuffleGrouping(SpoutBuilder.SPOUTID);

        // Secondary bolts should receive respective streams from primary/first bolt
        builder.setBolt(QUESTIONBOLT, questionBolt).shuffleGrouping(MASTERBOLT, QUESTIONSTREAM);
        builder.setBolt(EXCLAIMBOLT, exclaimBolt).shuffleGrouping(MASTERBOLT, EXCLAIMSTREAM);

        Config conf = new Config();
        conf.setNumWorkers(1);
        conf.put(Config.TOPOLOGY_STATS_SAMPLE_RATE, 1.00);

        // Run in localmode without UI
        // LocalCluster cluster = new LocalCluster();
        // cluster.submitTopology("storm-kafka-topology-test", conf, builder.createTopology());
        StormSubmitter.submitTopology("storm-kafka-topology-test", conf, builder.createTopology());
    }

    public static void main(String[] args) throws Exception {
        Topology ingestionTopology = new Topology();
        ingestionTopology.submitTopology();
    }
}
