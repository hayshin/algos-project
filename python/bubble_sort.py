import time
import tracemalloc
import random

def bubbleSort(array):
  n = len(array)
  for i in range(n-1, 0, -1):
    for j in range(i):
      if array[j] > array[j + 1]:
        array[j], array[j+1] = array[j+1], array[j]

# Generate an array of size 100,000 with random integers
size = 50000
# array = [random.randint(0, size) for _ in range(size)]
array =[i for i in range(size, 0, -1)]

# Start memory tracking
# tracemalloc.start()

# Start timer
# start_time = time.time()

# Perform Bubble Sort
bubbleSort(array)

# Stop timer and calculate running time
# end_time = time.time()
# elapsed_time_ms = (end_time - start_time) * 1000

# Stop memory tracking and calculate memory usage
# current_memory, peak_memory = tracemalloc.get_traced_memory()
# tracemalloc.stop()

# Convert memory usage to MB
# memory_usage_mb = peak_memory / (1024 * 1024)

# Results
# print(elapsed_time_ms)
# print(memory_usage_mb)
