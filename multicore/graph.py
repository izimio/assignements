import matplotlib.pyplot as plt

# Number of threads
threads = [1, 2, 4, 6, 8, 10, 12, 14, 16, 32]

# Execution times in milliseconds
static_block = [2429, 1857, 1150, 941, 905, 772, 799, 846, 881, 772]
static_cyclic = [2273, 1243, 655, 789, 664, 668, 682, 735, 672, 717]
dynamic = [2344, 1217, 657, 648, 672, 664, 663, 650, 659, 670]

# Plotting
plt.figure(figsize=(10, 6))
plt.plot(threads, static_block, marker='o', label='Static (Block)')
plt.plot(threads, static_cyclic, marker='s', label='Static (Cyclic, Size 10)')
plt.plot(threads, dynamic, marker='^', label='Dynamic (Size 10)')

# Labels and Title
plt.xlabel('Number of Threads')
plt.ylabel('Execution Time (ms)')
plt.title('Execution Time vs. Number of Threads')
plt.legend()
plt.grid(True)
plt.xticks(threads)

# Display the plot
plt.tight_layout()
plt.show()
