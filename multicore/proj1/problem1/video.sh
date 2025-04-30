#!/bin/bash

JAVA_FILES=("pc_dynamique.java" "pc_static_block.java" "pc_static_cyclic.java")

THREAD_COUNTS=(2 4 12)

NUM_END=200000

# Loop over each Java file
for JAVA_FILE in "${JAVA_FILES[@]}"; do
    CLASS_NAME="${JAVA_FILE%.java}"

    echo "========================================"
    echo "🔧 Compiling: $JAVA_FILE"
    echo "========================================"
    javac "$JAVA_FILE" || { echo "❌ Compilation failed for $JAVA_FILE"; continue; }

    echo "✅ Compilation successful for $JAVA_FILE"
    echo ""

    # Run the Java program with selected thread counts
    for THREADS in "${THREAD_COUNTS[@]}"; do
        echo "----------------------------------------"
        echo "🚀 Running $CLASS_NAME with $THREADS thread(s)"
        echo "----------------------------------------"
        java "$CLASS_NAME" "$THREADS" "$NUM_END"
        echo ""
    done

    echo "========================================"
    echo "✅ Finished running: $CLASS_NAME"
    echo "========================================"
    echo ""
done

