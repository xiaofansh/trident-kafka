package com.zaixuan.trident.kafka;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import kafka.consumer.ConsumerConfig;

/*
 * load the kafka consumer config file
 */
public class Prop {
	/*
	 * load the properties file
	 */
	public static ConsumerConfig GetConsumerConfig(String FilePath)
	{
		Properties props = new Properties();
		try {
			FileInputStream inputFile = new FileInputStream(FilePath);
            props.load(inputFile);
            
        } 
		catch (FileNotFoundException ex) {
			System.out.println("Can't find the property file");
			ex.printStackTrace();
		}
		
		catch (IOException e) {
			System.out.println("Fail to load the property file");
            e.printStackTrace();
        }
		
        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        return consumerConfig;
	}

}
