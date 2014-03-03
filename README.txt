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

---

To Run:

- This was compiled using the default Java on the CSIF machines, and run with Hadoop 1.1.1 (also on the CSIF machines).

- NOTE that the output has formatted that for every bigram combination it prints 'word1 word2 number_of_occurances', with the single quotes. Words at the end of a line do not have \n or newline printed with it.

- Assuming Hadoop has been set up correctly, run this command to compile the java with the appropriate mkdir bigram_classes; javac -classpath ${HADOOP_HOME}/hadoop-core-${HADOOP_VERSION}.jar -d bigram_classes BigramCount.java

You can/should replace ${HADOOP_HOME} and ${HADOOP_VERSION} with whatever your respective  env variables are.

- You can then create the jar by running the command: jar cvf ${HOME_DIRECTORY}/bigramcount.jar -C bigram_classes/ .

Make sure to change ${HOME_DIRECTORY} to whatever directory you want the jar to be.

- To run it in Hadoop, use this command: bin/hadoop jar ${HOME_DIRECTORY}/bigramcount.jar org.myorg.BigramCount input output/bigramcount

Again, make sure ${HOME_DIRECTORY} is wherever the bigramcount.jar is. This also assumes that you have the appropriate input and output folders already set up in Hadoop. You can use bin/hadoop fs -mkdir ${FOLDERS} to do so if you haven't already, replacing ${FOLDERS} with the correct folder names. 

Note that depending on your settings, you can probably run just 'hadoop' instead of 'bin/hadoop'.

- View output with: bin/hadoop fs -cat ${OUTPUT_DIRECTORY}/part-00000, replacing ${OUTPUT_DIRECTORY} as needed.