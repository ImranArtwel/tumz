package com.redis.aggregation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import redis.clients.jedis.Jedis;

public class PingIP {
	
	public static void runSystemCommand(String command) {

		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader inputStream = new BufferedReader(
					new InputStreamReader(p.getInputStream()));

			String s = "";
			String[] val = {};
			// reading output stream of the command
			while ((s = inputStream.readLine()) != null) {
		        System.out.println(s);
		        
		        val = s.split(" ");
		  
		       /* if(s.contains("Mbits/sec")){
		Pattern pattern = Pattern.compile("([0-9]*.[0-9]\\s[a-zA-Z]*.[a-z]*)");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find())
		{
		    System.out.println("matcher found "+matcher.group(1));
		}
	 }	*/ 
			}
			
			System.out.println("bandwidth = "+val[val.length-2]);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		String ip = "10.0.90.105";
		runSystemCommand("iperf -c " + ip);
        //System.out.println("hello");
		
	
	}
}


