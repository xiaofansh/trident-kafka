package com.zaixuan.mysql.model;

import java.io.Serializable;

public class TransactionInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6184704098060636020L;
	public String tID;
	public int xID;
	public int buyerID;
	public int amount;
	
	public void SetTid(String tID){
        this.tID=tID;
    }
	
	public String GettID()
	{
		return this.tID;
	}
	
	public void SetXid(int xid){
        this.xID=xid;
    }
	
	public int GetXid()
	{
		return this.xID;
	}
	
	public void SetbuyerID(int buyerID){
        this.buyerID = buyerID;
    }
	
	public long GetbuyerID()
	{
		return this.buyerID;
	}
	
	public void SetAmount(int amount){
        this.amount = amount;
    }
	
	public long GetAmount()
	{
		return this.amount;
	}
}
