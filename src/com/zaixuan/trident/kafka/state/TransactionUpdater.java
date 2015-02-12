package com.zaixuan.trident.kafka.state;

import java.util.ArrayList;
import java.util.List;

import storm.trident.operation.TridentCollector;
import storm.trident.state.BaseStateUpdater;
import storm.trident.tuple.TridentTuple;

public class TransactionUpdater extends BaseStateUpdater<TransactionDB> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8929242551487016514L;

	public void updateState(TransactionDB state, List<TridentTuple> tuples, TridentCollector collector) {

    	System.out.println("update:");
    	List<Integer> buyerIDs = new ArrayList<Integer>();
        List<Integer> amounts = new ArrayList<Integer>();
        for(TridentTuple t: tuples) {
        	buyerIDs.add(t.getInteger(0));
        	amounts.add(t.getInteger(1));
        }
        state.createTransaction(buyerIDs,amounts);
    }
}