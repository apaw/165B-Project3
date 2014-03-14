package org.myorg;
 	
import java.io.IOException;
import java.util.*;
 	
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
 	
 	public class TriangleCount {
 	
 	   public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
 	
 	     private Text word = new Text();
 	
 	     public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
 	       String line = value.toString();
 	       StringTokenizer tokenizer = new StringTokenizer(line);
	       StringTokenizer tokenizer2 = new StringTokenizer(line);
		if (tokenizer2.hasMoreTokens()) {
		tokenizer2.nextToken();
		}
 	       while (tokenizer.hasMoreTokens()) {
		if (tokenizer2.hasMoreTokens()) {
	         word.set('\'' + tokenizer.nextToken() + ' ' +  tokenizer2.nextToken() + '\'');
		}
		else {
		 word.set('\'' + tokenizer.nextToken() + '\'');
		}
 	         output.collect(value, value);
 	       }
 	     }
 	   }
 	
 	   public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
 
   	    private Text word1 = new Text();
   	    private Text word2 = new Text();
   	    private Text werd1 = new Text();
   	    private Text werd2 = new Text();

 	     public void reduce(Text key, Iterator<Text> word, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
 	    
 	       word1.set("\"" + "none" + "\"");
 	       output.collect(key, word1);

	    StringTokenizer itr1 = new StringTokenizer(word.toString());
        StringTokenizer itr2 = new StringTokenizer(word.toString());

        while(itr1.hasMoreTokens()) {
        	werd1.set(itr1.nextToken());
        	while(itr2.hasMoreTokens()) {
        		werd2.set(itr2.nextToken());
 	       		if(werd1 != werd2) //prevent duplicates {
 	       			word2.set(werd1 + " " + werd2);
 	       			output.collect(word2, key);
 	       		}
 	       	}
 	       }
 	     
 	   }
 	
 	   public static void main(String[] args) throws Exception {
 	     JobConf conf = new JobConf(TriangleCount.class);
 	     conf.setJobName("trianglecount");
 	
 	     conf.setOutputKeyClass(Text.class);
 	     conf.setOutputValueClass(Text.class);
 	
 	     conf.setMapperClass(Map.class);
 	     conf.setCombinerClass(Reduce.class);
 	     conf.setReducerClass(Reduce.class);
 	
 	     conf.setInputFormat(TextInputFormat.class);
 	     conf.setOutputFormat(TextOutputFormat.class);
 	
 	     FileInputFormat.setInputPaths(conf, new Path(args[0]));
 	     FileOutputFormat.setOutputPath(conf, new Path(args[1]));
 	
 	     JobClient.runJob(conf);
 	   }
 	}
