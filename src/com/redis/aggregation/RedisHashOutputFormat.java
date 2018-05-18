package com.redis.aggregation;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.NullOutputFormat;

import redis.clients.jedis.Jedis;

public class RedisHashOutputFormat extends OutputFormat<Text, Text> {
	
	//static configuration variable and methods
	public static final String REDIS_HOSTS_CONF = "mapred.redishashoutputformat.hosts";
	public static final String REDIS_HASH_KEY_CONF = "mapred.redishashinputformat.key";
	
	
	public static void setRedisHosts(Job job, String hosts) {
		job.getConfiguration().set(REDIS_HOSTS_CONF, hosts);
	}
	
	public static void setRedisHashKey(Job job, String hashKey) {
		job.getConfiguration().set(REDIS_HASH_KEY_CONF, hashKey);
	}
	
	// This method returns an instance of a RecordWriter for the task.
	@Override
	public RecordWriter<Text, Text> getRecordWriter(TaskAttemptContext job)throws IOException, InterruptedException {
		
		String hashKey = job.getConfiguration().get(REDIS_HASH_KEY_CONF);
		String csvHosts = job.getConfiguration().get(REDIS_HOSTS_CONF);
		
		return new RedisHashRecordWriter(hashKey, csvHosts);
	}
	
	// This method is used on the front-end prior to job submission to ensure everything is configured correctly
	@Override
	public void checkOutputSpecs(JobContext job) throws IOException,InterruptedException {
		
		String hosts = job.getConfiguration().get(REDIS_HOSTS_CONF);
		if (hosts == null || hosts.isEmpty()) {
		throw new IOException(REDIS_HOSTS_CONF + " is not set in configuration.");
		}
		
		String hashKey = job.getConfiguration().get(REDIS_HASH_KEY_CONF);
		if (hashKey == null || hashKey.isEmpty()) {
		throw new IOException(REDIS_HASH_KEY_CONF + " is not set in configuration.");
		}
		
	}

	// The output committer is used on the back-end to commit output
	@Override
	public OutputCommitter getOutputCommitter(TaskAttemptContext context)throws IOException, InterruptedException {
		
		return (new NullOutputFormat<Text, Text>()).getOutputCommitter(context);
	}
	
	

}
