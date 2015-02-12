package com.zaixuan.mysql.DAL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import snaq.db.ConnectionPool;

import com.zaixuan.mysql.model.UserInfo;

public class UserDAL {
	
	static String url = "jdbc:mysql://192.168.75.104:3306/test";
	static ConnectionPool pool = new ConnectionPool("local",
	        1000, 10000, 10000, 18000, url, "root", "root");
	static String UserName = "root";
	static String Password = "root";
	static Connection conn = null;

	public static boolean CreateUser(UserInfo uinfo) throws SQLException
	{
		if(conn ==null)
		  conn = DAL.getConnection("db1.properties","pooltest");
		String insql="insert into user(userid,amt_bought) values(?,?)";
		PreparedStatement ps=conn.prepareStatement(insql);
		ps.setInt(1, uinfo.GetUserID());
		ps.setInt(2, uinfo.amount);
		int result=ps.executeUpdate();
		conn.close();
		if(result>0)
           return true;
		else return false;
	}
	
	  public static void UpdateUserAmount(UserInfo userInfo)
	  {
		  try
		  {
		     if(conn == null)
		       conn = DAL.getConnection("db1.properties","pooltest");
		     CallableStatement stmt = conn.prepareCall("{call UpdateUserAmount(?,?,?)}"); 
		     stmt.setString(1,userInfo.tID);
	         stmt.setInt(2, userInfo.UserID);
	         stmt.setInt(3, userInfo.amount);
	         stmt.execute();
	         if (stmt != null)
             stmt.close();
		  } 
	        catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
			
	  }
	  
	  public static void UpdateUserAmount(UserInfo userInfo,Connection conn)
	  {
		  try
		  {
		     CallableStatement stmt = conn.prepareCall("{call UpdateUserAmount(?,?,?)}"); 
		     stmt.setString(1,userInfo.tID);
	         stmt.setInt(2, userInfo.UserID);
	         stmt.setInt(3, userInfo.amount);
	         stmt.execute();
	         if (stmt != null)
             stmt.close();
		  } 
	        catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
			
	  }
	
}
