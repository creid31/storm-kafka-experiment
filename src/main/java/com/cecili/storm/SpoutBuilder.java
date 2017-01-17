/**
 * Copyright 2016 NCR Corporation
 */
package com.cecili.storm;

import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;

/**
 * @author Cecili Reid (cr250220) on Jan 16, 2017
 *
 */
public class SpoutBuilder {

    public static final String SPOUTID = "kafka-spout";

    public KafkaSpout buildKafkaSpout() {
        BrokerHosts hosts = new ZkHosts("localhost:2181");
        SpoutConfig spoutConfig = new SpoutConfig(hosts, "test_storm_kafka", "/kafka", "test_group");
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);
        return kafkaSpout;
    }
}
