package com.zaixuan.trident.kafka.state;

import java.util.Map;

import storm.trident.state.State;
import storm.trident.state.StateFactory;

@SuppressWarnings("serial")
public class TransactionDBFactory implements StateFactory {
	   public State makeState(Map conf, int partitionIndex, int numPartitions) {
		   System.out.println("dddd");
	      return new TransactionDB();
	   } 
}