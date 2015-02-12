package com.zaixuan.trident.kafka;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

@SuppressWarnings("serial")
public class Split extends BaseFunction {
	   public void execute(TridentTuple tuple, TridentCollector collector) {
		   
		   String delims = "[,]+";
		   String[] tokens = tuple.getString(0).split(delims);
		   //System.out.println("Split: " + tokens[0]+":"+tokens[1]+":"+tokens[2]);
	       collector.emit(new Values(tokens[0],Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2])));
	       
	      
	   }
	   
	}
