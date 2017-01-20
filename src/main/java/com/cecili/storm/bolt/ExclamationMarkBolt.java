/**
 * Copyright 2016 NCR Corporation
 */
package com.cecili.storm.bolt;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;

/**
 * ExclaimationBolt adds exclaimation marks to the phrase sent to it via MasterBolt
 * 
 * @author Cecili Reid (cr250220) on Jan 16, 2017
 */
public class ExclamationMarkBolt implements IRichBolt {
    private static final long serialVersionUID = 1L;

    private OutputCollector collector;

    /* (non-Javadoc)
     * @see backtype.storm.task.IBolt#cleanup()
     */
    public void cleanup() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see backtype.storm.task.IBolt#execute(backtype.storm.tuple.Tuple)
     */
    public void execute(Tuple tuple) {
        String phrase = tuple.getStringByField("phrase");
        System.out.println("ExclamationMarkBolt received" + phrase);
        String new_phrase = phrase + "!!!";
        System.out.println("Emitted" + new_phrase);
        collector.ack(tuple);
    }

    /* (non-Javadoc)
     * @see backtype.storm.task.IBolt#prepare(java.util.Map, backtype.storm.task.TopologyContext, backtype.storm.task.OutputCollector)
     */
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    /* (non-Javadoc)
     * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("finalPhrase"));
    }

    /* (non-Javadoc)
     * @see backtype.storm.topology.IComponent#getComponentConfiguration()
     */
    public Map<String, Object> getComponentConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }

}
