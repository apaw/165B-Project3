*This is the repo that will be used for project 3 in ECS165B

MEMBERS: 
HOLLENBECK, Jonathan
PAW, Alicia 

---

Files Included:

- BigramCount.java - The bigram counter source code
- BigramCount_output.txt - Output from Hadoop of the bigram counter
- WordCount_output.txt - Output from Hadoop of the word counter
- BigramCount.jar - The compiled jar from the source code.
- TriangleCount.java - The triangle counter source code
- output.txt - The output from Hadoop of the triangle counter

---

To set up Whirr: 

-Set the following environment variables: 

export HADOOP_PREFIX=~/hadoop
export HADOOP_CONF_DIR=~/.whirr/hadoop-ec2
export PATH=$HADOOP_PREFIX/bin:$PATH

-After downloading Whirr, simply run the following to start the cluster.  
-Configure whirr.instance.templates in hadoop-ec2.properties to change the # of nodes in the cluster. 

bin/whirr launch-cluster --config ~/hadoop-ec2.properties

-After running, make sure to shut down the cluster.

bin/whirr destroy-cluster --config ~/hadoop-ec2.properties


To Run:

- This was compiled using the default Java on the CSIF machines, and run with Hadoop 1.2.1 (also on the CSIF machines).

-To place the file on the server, use
bin/hadoop fs -put enron.txt enron.txt

- Assuming Hadoop has been set up correctly, run this command to compile the java with the appropriate mkdir bigram_classes; javac -classpath ${HADOOP_HOME}/hadoop-core-${HADOOP_VERSION}.jar -d triangle_classes TriangleCount.java

You can/should replace ${HADOOP_HOME} and ${HADOOP_VERSION} with whatever your respective  env variables are.

- You can then create the jar by running the command: jar cvf ${HOME_DIRECTORY}/trianglecount.jar -C triangle_classes/ .

Make sure to change ${HOME_DIRECTORY} to whatever directory you want the jar to be.

- To run it in Hadoop, use this command: bin/hadoop jar ${HOME_DIRECTORY}/trianglecount.jar org.myorg.TriangleCount input tmp output

Again, make sure ${HOME_DIRECTORY} is wherever the bigramcount.jar is. This also assumes that you have the appropriate input and output folders already set up in Hadoop. You can use bin/hadoop fs -mkdir ${FOLDERS} to do so if you haven't already, replacing ${FOLDERS} with the correct folder names. 

Note that depending on your settings, you can probably run just 'hadoop' instead of 'bin/hadoop'.

- View output with: bin/hadoop fs -cat ${OUTPUT_DIRECTORY}/part-00000, replacing ${OUTPUT_DIRECTORY} as needed.

