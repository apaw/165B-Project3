*This is the repo that will be used for project 3 in ECS165B

MEMBERS: 
HOLLENBECK, Jonathan
PAW, Alicia 

---

Files Included:

- TriangleCount.java: Our triangle count code.
- enron.txt: Our normalized enron email data. While you don't have to use this, the instructions below assume that it will.
- TopTenNodes.txt: A list of the ten most frequently occuring nodes from our triangle, and the explanation of our procedure.
- node_counts.txt: Just for your reference, this is the node count file we generated to determine the top ten nodes, sorted from decreasing -> increasing frequency.
- Runtimes_Graph: Graph of runtimes for 2-,4-,6- node clusters. Color-coded for your convenience. Blue = runtime of first job, Red = runtime of second job, Green = total runtime for both jobs.
- Runtimes_data: For your ref, the data values we plugged into the graph.

---

To Set Up Whirr (if you haven't already): 

-Set the following environment variables: 

export HADOOP_PREFIX=~/hadoop
export HADOOP_CONF_DIR=~/.whirr/hadoop-ec2
export PATH=$HADOOP_PREFIX/bin:$PATH

-After downloading Whirr, simply run the following to start the cluster.  
-Configure whirr.instance.templates in hadoop-ec2.properties to change the # of nodes in the cluster. 

bin/whirr launch-cluster --config ~/hadoop-ec2.properties

-After running, make sure to shut down the cluster.

bin/whirr destroy-cluster --config ~/hadoop-ec2.properties

---

To Run:

- This was compiled using the default Java on the CSIF machines, and run with Hadoop 1.2.1 (also on the CSIF machines).

-To place the file on the server, use
bin/hadoop fs -put enron.txt enron.txt

- Assuming Hadoop has been set up correctly, run this command to compile the java:
mkdir triangle_classes; javac -classpath ${HADOOP_HOME}/hadoop-core-${HADOOP_VERSION}.jar -d triangle_classes TriangleCount.java

You can/should replace ${HADOOP_HOME} and ${HADOOP_VERSION} with whatever your respective  env variables are.

- You can then create the jar by running the command: jar cvf ${HOME_DIRECTORY}/trianglecount.jar -C triangle_classes/ .

Make sure to change ${HOME_DIRECTORY} to whatever directory you want the jar to be.

- To run it in Hadoop, use this command: bin/hadoop jar ${HOME_DIRECTORY}/trianglecount.jar org.myorg.TriangleCount <input> <tmp> <output>

Again, make sure ${HOME_DIRECTORY} is wherever the jar is. Replace <input> <tmp> and <output> with wherever you put the enron file, a tmp directory, and a desired output directory.

Note that depending on your settings, you can probably run just 'hadoop' instead of 'bin/hadoop'.

- View output with: bin/hadoop fs -cat ${OUTPUT_DIRECTORY}/part-00000, replacing ${OUTPUT_DIRECTORY} as needed.

