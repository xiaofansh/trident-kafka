package com.zaixuan.trident.kafka.state;

import java.util.List;

import com.zaixuan.mysql.DAL.TransactionDAL;
import com.zaixuan.mysql.model.TransactionInfo;

import storm.trident.state.State;

public class TransactionDB implements State {
    public void beginCommit(Long txid) {  
    	System.out.println("dsds");
    }
    
    public void commit(Long txid) {    
    }
    
    public void createTransaction(List<Integer> buyerIDs, List<Integer> amounts) {
      // code to access database and set location
    	
    	for(int i=0;i<buyerIDs.size();i++)
    	{
    	  TransactionInfo tinfo = new TransactionInfo();
          tinfo.SetbuyerID(buyerIDs.get(i));
          tinfo.SetAmount(amounts.get(i));
          //TransactionDAL.InsertTransaction(tinfo);
    	}
    }
    
    //public String getLocation(long userId) {
      // code to get location from database
   // }
}