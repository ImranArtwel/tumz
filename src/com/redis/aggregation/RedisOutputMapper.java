package com.redis.aggregation;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper;


public class RedisOutputMapper extends Mapper<Object, Text, Text, Text> {

	private final static IntWritable one = new IntWritable(1);
	
	
	
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

		String valueString = value.toString();
		String[] SingleCountryData = valueString.split(",");
		context.write(new Text(SingleCountryData[7]), new Text("1"));
	}

}
