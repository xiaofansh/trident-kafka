package com.zaixuan.trident.kafka;

import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

import com.zaixuan.mysql.DAL.TransactionDAL;
import com.zaixuan.mysql.model.TransactionInfo;
@SuppressWarnings("serial")
public class Transaction extends BaseFilter {
	private TransactionInfo tinfo = new TransactionInfo();
    @Override
    public boolean isKeep(TridentTuple tuple) {
    	//System.out.println("Transaction: " + tuple.toString());

        tinfo.tID = tuple.getString(0);
        tinfo.buyerID = tuple.getInteger(1);
        tinfo.amount= tuple.getInteger(2);
        TransactionDAL.InsertTransaction(tinfo);
       // TransactionDAL.InsertTransaction(tinfo, conn);
        return true;
    }
    
}
