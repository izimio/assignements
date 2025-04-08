#!/bin/bash

# Compile the Java file
javac pc_dynamique.java

# Output file
OUTPUT_FILE="output.txt"
echo "==== Static Cyclic Parallel Prime Computation Results ====" > $OUTPUT_FILE
echo "Range: 1 to 200000" >> $OUTPUT_FILE
echo "" >> $OUTPUT_FILE

# Fixed range to check for primes
NUM_END=200000

# List of thread counts
THREAD_COUNTS=(1 2 4 6 8 10 12 14 16 32)

# Run the program with each thread count
for THREADS in "${THREAD_COUNTS[@]}"; do
    echo ">>> Running with $THREADS thread(s)..." >> $OUTPUT_FILE
    java pc_dynamique $THREADS $NUM_END >> $OUTPUT_FILE
    echo "" >> $OUTPUT_FILE
done
