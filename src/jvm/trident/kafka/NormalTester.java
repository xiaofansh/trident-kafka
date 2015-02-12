package trident.kafka;


import java.util.Properties;

import kafka.consumer.ConsumerConfig;

import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Debug;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;

public class NormalTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		 TridentTopology topology = new TridentTopology();
		 Properties props = new Properties();
			props.put("zk.connect", "192.168.75.45:2181,192.168.75.55:2181,192.168.75.65:2181");
			props.put("zk.connectiontimeout.ms", "1000000");
			props.put("groupid", "f_group");
	        //props.put("autocommit.enable", "true");
	        props.put("autooffset.reset","smallest");
	        //props.put("zk.synctime.ms", "200");
	        props.put("autocommit.interval.ms", "10000");

	        String topic = "test";
			// Create the connection to the cluster
			ConsumerConfig consumerConfig = new ConsumerConfig(props);
	        NormalKafkaConfig kafkaConf = new NormalKafkaConfig(NormalKafkaConfig.SerialzableCustomConfig.fromConsumerConfig(consumerConfig),topic);
	        kafkaConf.scheme = new StringScheme();
	        //kafkaConf.
	        topology.newStream("mykafka", new TransactionalTridentNormalKafkaSpout(kafkaConf))
//	                .aggregate(new Count(), new Fields("count"))
	                .each(new Fields("str"), new Debug());
	        
	        LocalCluster cluster = new LocalCluster();
	        
	        StormTopology topo = topology.build();
	        
	        cluster.submitTopology("kafkatest", new Config(), topo);
	}

}
