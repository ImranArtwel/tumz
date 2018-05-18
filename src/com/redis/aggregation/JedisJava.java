package com.redis.aggregation;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

public class JedisJava {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf, args)
				.getRemainingArgs();

		if (otherArgs.length != 3) {
			System.err
					.println("Usage: RedisOutput <user data> <redis hosts> <hash name>");
			System.exit(1);
		}

		Path inputPath = new Path(otherArgs[0]);
		String hosts = otherArgs[1];
		String hashName = otherArgs[2];
		//String output = "/home/imran/Documents";
		
		float totalKVs = 0;
		final Logger LOG = Logger.getLogger(RedisHashRecordWriter.class);
		
		// jedis key-value iterator
		Iterator<Entry<String, String>> keyValueMapIter = null;

		Job job = Job.getInstance(conf, "Redis Output");
		job.setJarByClass(JedisJava.class);

		job.setMapperClass(RedisOutputMapper.class);
		job.setNumReduceTasks(0);

		job.setInputFormatClass(TextInputFormat.class);
		TextInputFormat.setInputPaths(job, inputPath);

		job.setOutputFormatClass(RedisHashOutputFormat.class);
		RedisHashOutputFormat.setRedisHosts(job, hosts);
		RedisHashOutputFormat.setRedisHashKey(job, hashName);
		

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.waitForCompletion(true);
		
		//------------------------------------------job 2--------------------------------------
		
		
		/*// test the jedis
				Jedis jedis = new Jedis("127.0.0.1");
				System.out.println("Value for key artwel = "+ jedis.hget(hashName,"artwel"));
				totalKVs = jedis.hlen(hashName);
				keyValueMapIter = jedis.hgetAll(hashName).entrySet().iterator();
				LOG.info("Got " + totalKVs + " from " + hashName);
				
				jedis.quit(); */
				
				

	}

}
