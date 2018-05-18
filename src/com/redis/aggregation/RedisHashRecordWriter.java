package com.redis.aggregation;

import com.redis.aggregation.MapOutAggregation;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

public class RedisHashRecordWriter extends RecordWriter<Text, Text> {

	// This map is used to map an integer to a Jedis instance
	private HashMap<Integer, Jedis> jedisMap = new HashMap<Integer, Jedis>();

	// This is the name of the Redis hash
	private String hashKey = null;
	
	// variale for key and value
	private Text key = new Text();
	private Text value = new Text();
	
	//map to store the aggregated value
	private Map<String, Integer> map;
	
	//store current entry during iteration
	private Entry<String, String> currentEntry = null;
	
	//variable for total key-value pairs in an instance
	 private float totalKVs = 0;
	 
	 //for logging/ printing out
	 private static final Logger LOG = Logger.getLogger(RedisHashRecordWriter.class);
	
	// jedis key-value iterator
	private Iterator<Entry<String, String>> keyValueMapIter = null;
	
    String csvHosts="";
	
	public RedisHashRecordWriter(String hashKey, String hosts) {
	this.hashKey = hashKey;
	csvHosts = hosts;
	LOG.info("the hash key value is "+ hashKey);

	// Create a connection to Redis for each host
	// Map an integer 0-(numRedisInstances - 1) to the instance
	int i=0;
	for (String host : hosts.split(",")) {
	Jedis jedis = new Jedis(host);
	jedis.connect();
	LOG.info(host + " connected successfully");
	jedisMap.put(i++, jedis);
	}
	}
	
	// The write method is what will actually write the key value pairs out to Redis
	public void write(Text key, Text value) throws IOException, InterruptedException {
	// Get the Jedis instance that this key/value pair will be written to.
	Jedis j = jedisMap.get(Math.abs(key.hashCode()) % jedisMap.size());
	LOG.info("savind data to "+j);
	int total;
     //get the map
	 map = getMap();
	 //get each key
	 String token = key.toString().trim();
	 
	 //check if map already contains the token (key)
	 if(map.containsKey(token))
	 {
		 //LOG.info("Putting new value into map");
		 total = map.get(token) + 1;
		 map.put(token, total);
		 
	 }
	 else {
		 map.put(token, 1);
	 }
	 
	//iterate the map and set new values in the jedis cache
		Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();
		  while(it.hasNext()) {
		   Map.Entry<String, Integer> entry = it.next();
		   String sKey = entry.getKey();
		   int total_value = entry.getValue().intValue();
		   j.hset(hashKey, sKey.toString(), Integer.toString(total_value));
		  }
	

	
	}
	

	
	public Map<String, Integer> getMap() {
		if(null == map) //lazy loading
			   map = new HashMap <String,Integer>();
			  return map;
	}

	/*public IntWritable getCurrentValue() throws IOException, InterruptedException {
		return value;
		}*/
	
	
	public void close(TaskAttemptContext context)
	throws IOException, InterruptedException {
	
		
	 	
	// For each jedis instance, disconnect it
	for (Jedis jedis : jedisMap.values())
	{
		//LOG.info("Total number for keys is => "+ jedis.hlen(hashKey));
		//jedis.flushDB();
	    jedis.disconnect();
	}
   }
}
