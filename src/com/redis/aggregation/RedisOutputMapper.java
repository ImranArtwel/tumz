package com.redis.aggregation;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Mapper;


public class RedisOutputMapper extends Mapper<LongWritable, Text, Text, Text> {

	private final static IntWritable one = new IntWritable(1);
	
	
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		//String valueString = value.toString();
		//String[] SingleCountryData = valueString.split(",");
		//context.write(new Text(SingleCountryData[7]), new Text("1"));
		String[] val = value.toString().split(",");
		
		for (String string : val) {
			context.write(new Text(string), new Text("1"));
		}
	}

}
