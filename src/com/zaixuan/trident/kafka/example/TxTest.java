package com.zaixuan.trident.kafka.example;

import com.zaixuan.trident.kafka.Prop;
import com.zaixuan.trident.kafka.Split;
import com.zaixuan.trident.kafka.Transaction;
import com.zaixuan.trident.kafka.User;

import kafka.consumer.ConsumerConfig;
import storm.trident.TridentTopology;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import trident.kafka.NormalKafkaConfig;
import trident.kafka.StringScheme;
import trident.kafka.TransactionalTridentNormalKafkaSpout;

public class TxTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		 TridentTopology topology = new TridentTopology();
		 String topic = "test3";
		 ConsumerConfig consumerConfig = Prop.GetConsumerConfig("/kafka.properties");
	     NormalKafkaConfig kafkaConf = new NormalKafkaConfig(NormalKafkaConfig.SerialzableCustomConfig.fromConsumerConfig(consumerConfig),topic);
	     kafkaConf.scheme = new StringScheme();
	     
	     topology.newStream("mykafka", new TransactionalTridentNormalKafkaSpout(kafkaConf))
         .each(new Fields("str"), new Split(),new Fields("tid","buyerID","amount"))
	     .each(new Fields("tid","buyerID","amount"), new Transaction())
	     .each(new Fields("tid","buyerID","amount"), new User())
	     .parallelismHint(60)
	     ;
	     
	      StormTopology topo = topology.build();
	      Config conf = new Config();
	 	  //conf.setDebug(true);
	      conf.setNumWorkers(2);
	      
	      if(args!=null && args.length > 0) {
	            StormSubmitter.submitTopology(args[0], conf, topo);
	        } else {
	        	LocalCluster cluster = new LocalCluster();
	  	        cluster.submitTopology("kafkatest", conf, topo);
	           // Utils.sleep(10000);
	           // cluster.killTopology("test");
	            //cluster.shutdown();
	        }
	}

}
