package com.redis.aggregation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingCommand {
	
	public static void runSystemCommand(String command) {

		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader inputStream = new BufferedReader(
					new InputStreamReader(p.getInputStream()));

			String s = "";
			// reading output stream of the command
			while ((s = inputStream.readLine()) != null) {
		System.out.println(s);
		/*Pattern pattern = Pattern.compile("([0-9]*.[0-9]\\s[a-zA-Z]*.[a-z]*)");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find())
		{
		    System.out.println("matcher found "+matcher.group(1));
		}*/
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		
		String ip = "10.0.90.105";
		runSystemCommand("iperf -r " + ip);
		//System.out.println("hey");
		/*Jedis jedis = new Jedis("10.0.90.105");
		jedis.connect();
		Long totalKVs = jedis.hlen("tumz");
		System.out.println("total keys = "+ totalKVs);
		System.out.println("Value for key Sweden = "+ jedis.hget("tumz","Sweden"));*/
		
		//String data = "94.6 Mbits/sec";
		

	
	}

}
