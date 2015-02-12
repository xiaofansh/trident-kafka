package com.zaixuan.trident.kafka;

import java.sql.Connection;
import java.sql.SQLException;

import storm.trident.operation.BaseFilter;
import storm.trident.topology.TransactionAttempt;
import storm.trident.tuple.TridentTuple;

import com.zaixuan.mysql.DAL.DAL;
import com.zaixuan.mysql.DAL.TransactionDAL;
import com.zaixuan.mysql.DAL.UserDAL;
import com.zaixuan.mysql.model.TransactionInfo;
import com.zaixuan.mysql.model.UserInfo;

@SuppressWarnings("serial")
public class User extends BaseFilter {
	
	//private Connection conn = null;
    private UserInfo uinfo = new UserInfo();
    public User()
    {
    	super();
    	//try {
			//conn = DAL.getConnection1("pooltest");
		//} catch (SQLException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
    }
		
	 public boolean isKeep(TridentTuple tuple)  {
	    	//System.out.println("User: " + tuple.toString());
			uinfo.tID = tuple.getString(0);
			uinfo.UserID = tuple.getInteger(1);
			uinfo.amount= tuple.getInteger(2);
            UserDAL.UpdateUserAmount(uinfo);
			//UserDAL.UpdateUserAmount(uinfo, conn);
            return true;
	    }
}
