package trident.kafka;

import backtype.storm.utils.Utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.Message;
import kafka.serializer.DefaultDecoder;
import storm.trident.operation.TridentCollector;
import storm.trident.topology.TransactionAttempt;

public class NormalKafkaUtils {
    
    
     public static Map emitPartitionBatchNew(NormalKafkaConfig config, int partition, TransactionAttempt attempt, TridentCollector collector, Map lastMeta, String topologyInstanceId) {
         
 		//consumerConnector.
    	 ConsumerConnector _connections = (ConsumerConnector) Consumer.createJavaConsumerConnector(config.config.consumerConfig);
 		 Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
 		    topicCountMap.put(config._topic, new Integer(1));
 		   
 		    Map<String, List<KafkaStream<Message>>> consumerMap = _connections.createMessageStreams(topicCountMap);
 		    //consumer.createMessageStreams()
 		    
 		    KafkaStream<Message> stream =  consumerMap.get(config._topic).get(0);
 		    ConsumerIterator<Message> it = stream.iterator();
 		   
 		    while(it.hasNext())
 		    {
 		    emit(config, attempt, collector, it.next().message());
 		    }
 		    
       
         Map newMeta = new HashMap();
         
         newMeta.put("instanceId", topologyInstanceId);
         return newMeta;
     }
     
     public static void emit(NormalKafkaConfig config, TransactionAttempt attempt, TridentCollector collector, Message msg) {
         List<Object> values = config.scheme.deserialize(Utils.toByteArray(msg.payload()));
         if(values!=null) {
             collector.emit(values);
         }
     }
}
