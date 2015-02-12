package trident.kafka;

import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import kafka.api.FetchRequest;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.Message;
import kafka.message.MessageAndOffset;
import kafka.serializer.DefaultDecoder;
import storm.trident.operation.TridentCollector;
import storm.trident.spout.IPartitionedTridentSpout;
import storm.trident.topology.TransactionAttempt;
import trident.kafka.KafkaConfig.StaticHosts;

import kafka.javaapi.consumer.ConsumerConnector;

public class TransactionalTridentNormalKafkaSpout implements IPartitionedTridentSpout<Map> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	NormalKafkaConfig _config;
    String _topologyInstanceId = UUID.randomUUID().toString();

    
    public TransactionalTridentNormalKafkaSpout(NormalKafkaConfig config) {
        _config = config;
    }
    
    class Coordinator implements IPartitionedTridentSpout.Coordinator {
        //@Override
        public long numPartitions() {
            return 1;
       }

        @Override
        public void close() {
            _config.coordinator.close();
        }

        @Override
        public boolean isReady(long txid) {
            return _config.coordinator.isReady(txid);
        }
    }
    
    class Emitter implements IPartitionedTridentSpout.Emitter<Map> {
    	//ConsumerConnector _connections;
        //int partitionsPerHost;
        
        public Emitter() {
            //_connections = (ConsumerConnector) Consumer.createJavaConsumerConnector(_config.config.consumerConfig);
                  
        }
        
        @SuppressWarnings("rawtypes")
		@Override
        public Map emitPartitionBatchNew(TransactionAttempt attempt, TridentCollector collector, int partition, Map lastMeta) {

            return NormalKafkaUtils.emitPartitionBatchNew(_config, partition, attempt, collector, lastMeta, _topologyInstanceId);
        }

        @SuppressWarnings("rawtypes")
		@Override
        public void emitPartitionBatch(TransactionAttempt attempt, TridentCollector collector, int partition, Map meta) {
            String instanceId = (String) meta.get("instanceId");
            if(instanceId.equals(_topologyInstanceId)) {
            	
         		//consumerConnector.
            	 ConsumerConnector _connections = (ConsumerConnector) Consumer.createJavaConsumerConnector(_config.config.consumerConfig);
         		 Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
         		    topicCountMap.put(_config._topic, 1);
         		    Map<String, List<KafkaStream<Message>>> consumerMap = _connections.createMessageStreams(topicCountMap);
         		    //consumer.createMessageStreams()
         		    
         		    KafkaStream<Message> stream =  consumerMap.get(_config._topic).get(0);
         		    ConsumerIterator<Message> it = stream.iterator();
         		   
         		    while(it.hasNext())
         		    {
         		    	NormalKafkaUtils.emit(_config, attempt, collector, it.next().message());
         		    }
                   
            }
        }
        
        @Override
        public void close() {
            //_connections.close();
        }
    }
    

    @Override
    public IPartitionedTridentSpout.Coordinator getCoordinator(Map conf, TopologyContext context) {
        return new Coordinator();
    }

    @Override
    public IPartitionedTridentSpout.Emitter getEmitter(Map conf, TopologyContext context) {
        return new Emitter();
    }

    @Override
    public Fields getOutputFields() {
        return _config.scheme.getOutputFields();
    }
    
    
    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}