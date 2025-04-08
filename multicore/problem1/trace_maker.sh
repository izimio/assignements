#!/bin/bash

# Clean output file
> output.txt

# Compile the Java program if needed
javac pc_static_block.java

# Run the program with thread counts from 1 to 32
for i in {1..32}
do
  echo "Running with $i thread(s)..." | tee -a output.txt
  java pc_static_block $i 200000 >> output.txt
  echo "-------------------------------" >> output.txt
done
