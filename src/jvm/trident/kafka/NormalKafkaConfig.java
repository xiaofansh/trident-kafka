package trident.kafka;

import backtype.storm.spout.RawScheme;
import backtype.storm.spout.Scheme;
import java.io.Serializable;
import kafka.consumer.ConsumerConfig;

public class NormalKafkaConfig implements Serializable {    
	@SuppressWarnings("serial")
	public static class SerialzableCustomConfig implements Serializable {        
        public ConsumerConfig consumerConfig;
        
        public static SerialzableCustomConfig fromConsumerConfig(ConsumerConfig consumerConfig) {
            return new SerialzableCustomConfig(consumerConfig);
        }
        
        public SerialzableCustomConfig(ConsumerConfig consumerConfig) {
            this.consumerConfig = consumerConfig;
        }

    }
	/**
	 * 
	 */
	private static final long serialVersionUID = 6811444820548435671L;
	SerialzableCustomConfig config;
	String _topic;
	public Scheme scheme = new RawScheme();
    public IBatchCoordinator coordinator = new DefaultCoordinator();

    public NormalKafkaConfig(SerialzableCustomConfig config,String topic) {
        this.config = config;
        this._topic = topic;
    }

}
