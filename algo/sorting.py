import os
import sys

from random_quicksort import quicksort
from merge_insertion_sort import merge_insertion_sort
from heap_sort import heap_sort

# Checks if the input file exists and raises an error if it doesn't
def check_file_exists(filepath):
    if not os.path.isfile(filepath):
        raise FileNotFoundError(f"The file '{filepath}' does not exist.")

def clean_output_file(filepath):
    if os.path.isfile(filepath):
        os.remove(filepath)

def check_errors():
    if len(sys.argv) != 3:
        raise ValueError("Usage: python sorting.py <input_file> <output_file>")
    
    clean_output_file(sys.argv[2])
    
    num_list = parse_input_file(sys.argv[1])
    return (num_list, sys.argv[2])

def parse_input_file(filepath):
    check_file_exists(filepath)
    with open(filepath, 'r') as f:
        lines = f.readlines()

    if len(lines) != 2:
        raise ValueError("Input file must have at least two lines.")

    # Parse the first line of the file and check if it's an correct integer
    try:
        expected_length = int(lines[0].strip())
    except ValueError:
        raise ValueError("First line must be an integer representing the list length.")

    # Same for second line, also parsing if the number of integers is correct with the one announced earlier
    elements = lines[1].strip().split()
    try:
        numbers = [int(num) for num in elements]
    except ValueError:
        raise ValueError("Second line must contain only integers.")

    if len(numbers) != expected_length:
        raise ValueError(f"Expected {expected_length} numbers, but got {len(numbers)}.")

    return numbers



def write_to_output_file(filepath, sorted_numbers):
    with open(filepath, 'a') as f:
        # if the file is empty, don't add \n
        if os.path.getsize(filepath) == 0:
            f.write(' '.join(map(str, sorted_numbers)))
        else:
            f.write('\n' + ' '.join(map(str, sorted_numbers)))

def is_sorted(arr):
    return all(arr[i] <= arr[i + 1] for i in range(len(arr) - 1))

try:
    (NUM_LIST, OUTPUT_FILE) = check_errors()
    merge_insertion_sort_ouput = merge_insertion_sort(NUM_LIST.copy())
    quicksort_output = quicksort(NUM_LIST.copy())
    heap_sort_output = heap_sort(NUM_LIST.copy())
    
    # Check if the outputs are sorted
    if not (is_sorted(merge_insertion_sort_ouput) and is_sorted(quicksort_output) and is_sorted(heap_sort_output)):
        raise ValueError("Sorting failed. The output is not sorted :(")

    write_to_output_file(OUTPUT_FILE, merge_insertion_sort_ouput)
    write_to_output_file(OUTPUT_FILE, quicksort_output)
    write_to_output_file(OUTPUT_FILE, heap_sort_output)
except (ValueError, FileNotFoundError) as e:
    print(e)
    sys.exit(0)


