package com.zaixuan.mysql.DAL;

import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import snaq.db.ConnectionPool;

import com.zaixuan.mysql.DAL.DAL;
import com.zaixuan.mysql.model.TransactionInfo;
public class TransactionDAL {

	static String url = "jdbc:mysql://192.168.75.102:3306/test";
	static ConnectionPool pool = new ConnectionPool("local",
	        1000, 10000, 10000, 18000, url, "root", "root");
	static Connection conn = null;
	
	/*
	public static boolean CreateTransaction(TransactionInfo tinfo)
	{
		boolean isTrue = false;
		try {
	    if(conn ==null)
		  conn = DAL.getConnection("pooltest");
		String insql="insert into transaction(buyer_id,amount) values(?,?)";
		PreparedStatement ps;
		ps = conn.prepareStatement(insql);
		ps.setInt(1, tinfo.buyerID);
		ps.setInt(2, tinfo.amount);
		int result=ps.executeUpdate();
		conn.close();
		if(result>0)
           isTrue = true;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isTrue;
	}
	*/
	
	public static void InsertTransaction(TransactionInfo tinfo)
	{
		try {
	    if(conn ==null)
		 conn = DAL.getConnection("db.properties","pooltest");
		CallableStatement stmt = conn.prepareCall("{call InsertTransaction(?,?,?)}"); 
		stmt.setString(1,tinfo.tID);
        stmt.setInt(2, tinfo.buyerID);
        stmt.setInt(3, tinfo.amount);
        stmt.execute();
        if (stmt != null)
            stmt.close();
      
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void InsertTransaction(TransactionInfo tinfo,Connection conn)
	{
		try {
		CallableStatement stmt = conn.prepareCall("{call InsertTransaction(?,?,?)}"); 
		stmt.setString(1,tinfo.tID);
        stmt.setInt(2, tinfo.buyerID);
        stmt.setInt(3, tinfo.amount);
        stmt.execute();
        if (stmt != null)
            stmt.close();
      
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
