package com.redis.aggregation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

public class MapOutAggregation {

	public static void main(String[] args) {
		
		Jedis jedis = new Jedis("localhost");
		System.out.println(jedis.hgetAll("hally"));
		
	}
	

}
