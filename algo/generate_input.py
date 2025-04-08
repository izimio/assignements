import random

def generate_input_file(filename, size=1000, min_val=0, max_val=150):
    numbers = [random.randint(min_val, max_val) for _ in range(size)]

    with open(filename, 'w') as f:
        f.write(f"{size}\n")
        f.write(' '.join(map(str, numbers)) + '\n')

    print(f"Generated {filename} with {size} numbers.")

generate_input_file("input.txt", size=1000)
