package com.zaixuan.trident.kafka.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MysqlTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
      int i = 2000;
      ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 20, 5,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(300),
				new ThreadPoolExecutor.DiscardOldestPolicy());
      
      for(int j=0;j < i; j++)
		{
    	  threadPool.execute(new Mysql());
		}
	}
}
