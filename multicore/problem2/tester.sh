#!/bin/bash

# Check if a Java file was provided
if [ -z "$1" ]; then
    echo "Usage: $0 <JavaFilename>"
    exit 1
fi

JAVA_FILE="$1"
CLASS_NAME="$1"
NUM_END=200000
THREAD_COUNTS=(1 2 4 6 8 10 12 14 16 32)

# Compile the Java file
javac "$JAVA_FILE" || { echo "Compilation failed"; exit 1; }

# Run the program with each thread count
for THREADS in "${THREAD_COUNTS[@]}"; do
    echo ">>> Running with $THREADS thread(s)..."
    java "$CLASS_NAME" "$THREADS" "$NUM_END"
    echo ""
done
