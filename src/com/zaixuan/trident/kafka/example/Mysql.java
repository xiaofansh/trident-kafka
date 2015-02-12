package com.zaixuan.trident.kafka.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import com.zaixuan.mysql.DAL.DAL;
import com.zaixuan.mysql.DAL.TransactionDAL;
import com.zaixuan.mysql.model.TransactionInfo;

public class Mysql implements Runnable {
	 
 	 Connection conn = null;
 	 TransactionInfo tinfo = new TransactionInfo();
 	 
 	 public Mysql()
 	 {
 		 try {
			conn = DAL.getConnection("db.properties","pooltest");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	 }
	 public void run() {
		
		  long messageNo = 0;
		  long t = System.currentTimeMillis();
		  long r = System.currentTimeMillis();
		  long time = r-t;
		  while(true)
		  {
	    	  messageNo++;
	    	  tinfo.tID = UUID.randomUUID().toString();
	    	  tinfo.buyerID = 3;
	    	  tinfo.amount = 2;
    		  TransactionDAL.InsertTransaction(tinfo,conn);
	    	  if(messageNo % 40000 ==0)
		      {
	    		  System.out.println(messageNo);
		    	  r = System.currentTimeMillis();
		    	  time = r-t;
		    	  System.out.println(time);
		    	  t = r;
		      }
		  }
		}	 
}
