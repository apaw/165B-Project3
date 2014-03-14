package org.myorg;
 	
import java.io.IOException;
import java.util.*;
 	
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
 	
 	public class TriangleCount {
 	
 	   public static class Map1 extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
 	
 	     private Text x = new Text();
	     private Text y = new Text(); 	

 	     public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
 	       String line = value.toString();
 	       StringTokenizer tokenizer = new StringTokenizer(line);
		if (tokenizer.hasMoreTokens()) {
			x.set(tokenizer.nextToken());
		}
		if (tokenizer.hasMoreTokens()) {
			y.set(tokenizer.nextToken());
		}
 	         output.collect(x, y); // return <x,y>
 	     }
 	   }
 	
 	   public static class Reduce1 extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
 
   	    private Text None = new Text();
   	    private Text Coord = new Text();
   	    private Text werd1 = new Text();
   	    private Text werd2 = new Text();

 	     public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		List<String> values_list = new ArrayList<String>(); 	    

 	       None.set("none");

		while (values.hasNext()) { // collect values from iterator, print none
			String x = key.toString();
			String y = values.next().toString();
			values_list.add(y);
			String tmp = x + "," + y;
			Coord.set(tmp);
 	       		output.collect(Coord, None); // for all y in Z, return <(x,y), none>
			Coord.clear();
		}

		for (int i = 0; i < values_list.size(); i++) { // for all (m,n) in Z x Z (supposedly)
			String m = values_list.get(i);
			Coord.set(m + "," + "17");
			for (int j = 0; j < values_list.size(); j++) {
				String n = values_list.get(j).toString();
				String tmp = m + "," + n;
				Coord.set(tmp);
				if (Integer.parseInt(m) < Integer.parseInt(n)) {
					output.collect(Coord, key); // if m < n, return <(m,n), x>
				}
				Coord.clear();
			}
		}
 	       }
 	     
 	   }

 		public static class Map2 extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> { 			
 	     		private Text xy = new Text();
	     		private Text z = new Text(); 	

 			public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
				String line = value.toString();
				StringTokenizer tokenizer = new StringTokenizer(line);
				if (tokenizer.hasMoreTokens()) {
					xy.set(tokenizer.nextToken());
					//System.out.println(xy);
				}
				if (tokenizer.hasMoreTokens()) {
					z.set(tokenizer.nextToken());
					//System.out.println(z);
				}
				output.collect(xy, z); // return <(x,y),z>
			}
		}	

		public static class Reduce2 extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
	 	
	 	private Text value = new Text();
	 	private Text out = new Text();

	    	private Text z = new Text(); 	
	    	private Text Coord = new Text();

	 	    public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
	 	    	out.set("");
			List<String> values_list = new ArrayList<String>();
	 	    	while (values.hasNext()) { // store iterator values for safe-keeping
	 	    		value = values.next();
				String tmp = value.toString();
				values_list.add(tmp);
			}

			if (values_list.contains("none")) { // if none is included, the edge exists
				String xy = key.toString();
				//System.out.println(xy);
				for (int i = 0; i < values_list.size(); i++) { // print out nodes
					String z = values_list.get(i);
					//System.out.println(xy + " " + z);
					if ( !(z.equals("none")) ) { // but skip the values of none
						String tmp = xy + "," + z;
						Coord.set(tmp);
	 	    				output.collect(Coord, out); // return <(x,y,z), "">
						Coord.clear();
					}
				}
	 	    	}
	 	    }
		}

 	   public static void main(String[] args) throws Exception {
 	     JobConf conf = new JobConf(TriangleCount.class);
 	     conf.setJobName("trianglecount1");
 	
 	     conf.setOutputKeyClass(Text.class);
 	     conf.setOutputValueClass(Text.class);
 	
 	     conf.setMapperClass(Map1.class);
 	     //conf.setCombinerClass(Reduce.class);
 	     conf.setReducerClass(Reduce1.class);
 	
 	     conf.setInputFormat(TextInputFormat.class);
 	     conf.setOutputFormat(TextOutputFormat.class);
 	
 	     FileInputFormat.setInputPaths(conf, new Path(args[0]));
 	     FileOutputFormat.setOutputPath(conf, new Path(args[1]));
 	
 	     JobClient.runJob(conf);

 	     JobConf conf2 = new JobConf(TriangleCount.class);
 	     conf2.setJobName("trianglecount2");

 	     conf2.setOutputKeyClass(Text.class);
 	     conf2.setOutputValueClass(Text.class);

 	     conf2.setMapperClass(Map2.class);
 	     conf2.setReducerClass(Reduce2.class);

 	     conf2.setInputFormat(TextInputFormat.class);
 	     conf2.setOutputFormat(TextOutputFormat.class);

 	     FileInputFormat.setInputPaths(conf2, new Path(args[1]));
 	     FileOutputFormat.setOutputPath(conf2, new Path(args[2]));

 	     JobClient.runJob(conf2);
 	   }
 	}
