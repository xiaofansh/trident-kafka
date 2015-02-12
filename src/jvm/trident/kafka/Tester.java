package trident.kafka;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.KillOptions;
import backtype.storm.generated.StormTopology;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;

import java.util.Arrays;
import java.util.List;

import com.zaixuan.trident.kafka.Split;
import com.zaixuan.trident.kafka.state.TransactionDBFactory;
import com.zaixuan.trident.kafka.state.TransactionUpdater;

import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;
import storm.trident.operation.builtin.Debug;


public class Tester {
    public static void main(String[] args) throws Exception {
        TridentTopology topology = new TridentTopology();
        List<String> hosts = Arrays.asList("192.168.75.102:9092");
        KafkaConfig kafkaConf = new KafkaConfig(KafkaConfig.StaticHosts.fromHostString(hosts, 10), "test");
        kafkaConf.scheme = new StringScheme();
        //kafkaConf.
        topology.newStream("mykafka", new TransactionalTridentKafkaSpout(kafkaConf))
//                .aggregate(new Count(), new Fields("count"))
                //.each(new Fields("str"), new Debug())
                .each(new Fields("str"), new Split(),new Fields("buyerID","amount"))
                .partitionPersist(new TransactionDBFactory(),new Fields("buyerID","amount"), new TransactionUpdater())
                ;
        LocalCluster cluster = new LocalCluster();
        
        StormTopology topo = topology.build();
        
        cluster.submitTopology("kafkatest", new Config(), topo);
        
        /*
        KillOptions killopts = new KillOptions();
        killopts.set_wait_secs(0);
        Utils.sleep(5000);
        cluster.killTopologyWithOpts("kafkatest", killopts);
        Utils.sleep(5000);

        cluster.submitTopology("kafkatest", new Config(), topo);        
        Utils.sleep(60000);
        */
    }
}
