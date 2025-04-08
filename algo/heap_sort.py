def max_heapify(arr, n, i):
    largest = i
    left = 2 * i + 1
    right = 2 * i + 2

    # Check if left child is larger than root
    if left < n and arr[left] > arr[largest]:
        largest = left

    # Check if right child is larger than largest so far
    if right < n and arr[right] > arr[largest]:
        largest = right

    # If largest is not root
    if largest != i:
        arr[i], arr[largest] = arr[largest], arr[i]  # swap
        max_heapify(arr, n, largest)  # recursively heapify the affected sub-tree


def build_max_heap(arr):
    n = len(arr)
    # Start from last non-leaf node and heapify each one
    for i in range(n // 2 - 1, -1, -1):
        max_heapify(arr, n, i)


def heap_sort(arr):
    n = len(arr)
    build_max_heap(arr)

    # Extract elements one by one from the heap
    for i in range(n - 1, 0, -1):
        arr[0], arr[i] = arr[i], arr[0]  # swap
        max_heapify(arr, i, 0)  # heapify the reduced heap
    return arr
