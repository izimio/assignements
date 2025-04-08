SCRIPT = sorting.py
INPUT = input.txt
OUTPUT = output.txt

all: run

run:
	python3 $(SCRIPT) $(INPUT) $(OUTPUT)

generate_input:
	python3 generate_input.py

.PHONY: all run clean

clean:
	find . -name "*.pyc" -delete
