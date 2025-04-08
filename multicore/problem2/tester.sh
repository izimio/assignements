#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: $0 <JavaFilename>"
    exit 1
fi

JAVA_FILE="$1"
CLASS_NAME="$1"
NUM_END=200000
THREAD_COUNTS=(1 2 4 6 8 10 12 14 16 32)

javac "$JAVA_FILE" || { echo "Compilation failed"; exit 1; }

for THREADS in "${THREAD_COUNTS[@]}"; do
    echo ">>> Running with $THREADS thread(s)..."
    java "$CLASS_NAME" "$THREADS" < mat/mat1000.txt
    echo ""
done


We are going to use this script to test with all thread types ! 
