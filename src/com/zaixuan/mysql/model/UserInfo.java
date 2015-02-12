package com.zaixuan.mysql.model;

import java.io.Serializable;

public class UserInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5050606521940597595L;
	public String tID;
	public int UserID;
	public int amount;
	
	public void SetTID(String tID){
        this.tID = tID;
    }
	
	public String GetTID()
	{
		return this.tID;
	}
	
	public void SetUserID(int userID){
        this.UserID=userID;
    }
	
	public int GetUserID()
	{
		return this.UserID;
	}
	
	public void SetAmount(int amount){
        this.amount = amount;
    }
	
	public long GetAmount()
	{
		return this.amount;
	}
}
