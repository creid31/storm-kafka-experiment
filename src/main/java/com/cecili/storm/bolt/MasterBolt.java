/**
 * Copyright 2016 NCR Corporation
 */
package com.cecili.storm.bolt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import com.cecili.storm.Topology;

/**
 * MasterBolt determines if the phrase is a question or statement and sends to the appropriate secondary bolt
 * 
 * @author Cecili Reid (cr250220) on Jan 16, 2017
 */
public class MasterBolt implements IRichBolt {
    private static final long serialVersionUID = 1L;

    private OutputCollector collector;

    /* (non-Javadoc)
     * @see backtype.storm.task.IBolt#prepare(java.util.Map, backtype.storm.task.TopologyContext, backtype.storm.task.OutputCollector)
     */
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        
    }

    /* (non-Javadoc)
     * @see backtype.storm.task.IBolt#execute(backtype.storm.tuple.Tuple)
     */
    public void execute(Tuple input) {
        String phrase = input.getString(0);
        String[] words = phrase.split(" ");
        ArrayList<String> comparisonWords = new ArrayList<String>(Arrays.asList(new String[] {
                "Are", "When", "What", "How", "Where"
        }));
        if(comparisonWords.contains(words[0])){
            collector.emit(Topology.QUESTIONSTREAM, input, new Values("question", phrase));
        } else {
            collector.emit(Topology.EXCLAIMSTREAM, input, new Values("delaration", phrase));
        }
        collector.ack(input);
    }

    /* (non-Javadoc)
     * @see backtype.storm.task.IBolt#cleanup()
     */
    public void cleanup() {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see backtype.storm.topology.IComponent#declareOutputFields(backtype.storm.topology.OutputFieldsDeclarer)
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declareStream(Topology.QUESTIONSTREAM, new Fields("type", "phrase"));
        declarer.declareStream(Topology.EXCLAIMSTREAM, new Fields("type", "phrase"));
    }

    /* (non-Javadoc)
     * @see backtype.storm.topology.IComponent#getComponentConfiguration()
     */
    public Map<String, Object> getComponentConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }

}
