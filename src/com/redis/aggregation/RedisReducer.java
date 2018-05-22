package com.redis.aggregation;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class RedisReducer extends Reducer<Text, Text, Text, IntWritable>{
	
	@Override
	protected void reduce(Text key, Iterable<Text> values, Context context)throws IOException, InterruptedException {

		int sum =0;
		//IntWritable newVal = new IntWritable();
		
		for (Text val : values) {
			
			sum +=Integer.parseInt(val.toString());
		}
		context.write(key,new IntWritable(sum));
	}

}
