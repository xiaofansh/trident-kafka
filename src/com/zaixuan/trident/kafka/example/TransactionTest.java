package com.zaixuan.trident.kafka.example;

import com.zaixuan.trident.kafka.Prop;
import com.zaixuan.trident.kafka.Split;
import com.zaixuan.trident.kafka.state.TransactionDBFactory;
import com.zaixuan.trident.kafka.state.TransactionUpdater;

import kafka.consumer.ConsumerConfig;
import storm.trident.TridentTopology;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import trident.kafka.NormalKafkaConfig;
import trident.kafka.StringScheme;
import trident.kafka.TransactionalTridentNormalKafkaSpout;

public class TransactionTest {

	public static void main(String[] args) throws Exception{
		 // TODO Auto-generated method stub
		 TridentTopology topology = new TridentTopology();
		 String topic = "test";
		 ConsumerConfig consumerConfig = Prop.GetConsumerConfig("kafka.properties");
	     NormalKafkaConfig kafkaConf = new NormalKafkaConfig(NormalKafkaConfig.SerialzableCustomConfig.fromConsumerConfig(consumerConfig),topic);
	     kafkaConf.scheme = new StringScheme();
	     
	     topology.newStream("mykafka", new TransactionalTridentNormalKafkaSpout(kafkaConf))
	     .each(new Fields("str"), new Split(),new Fields("buyerID","amount"))
	     .partitionPersist(new TransactionDBFactory(),new Fields("buyerID","amount"), new TransactionUpdater());
	                
	     LocalCluster cluster = new LocalCluster();
	     StormTopology topo = topology.build();
	     cluster.submitTopology("kafkatest", new Config(), topo);
	}
}
