def max_heapify(arr, n, i):
    largest = i
    left = 2 * i + 1
    right = 2 * i + 2

    # Check if left child is larger than root
    if left < n and arr[left] > arr[largest]:
        largest = left

    # else check if right is bigger than the curr largest
    if right < n and arr[right] > arr[largest]:
        largest = right

    # if largest is still not root then...
    if largest != i:
        arr[i], arr[largest] = arr[largest], arr[i]  # swap
        max_heapify(arr, n, largest)  # recursively heapify the affected sub-tree


def build_max_heap(arr):
    n = len(arr)
    # heapify all last non leaf
    for i in range(n // 2 - 1, -1, -1):
        max_heapify(arr, n, i)


def heap_sort(arr):
    n = len(arr)
    build_max_heap(arr)

    # Extract elements one by one from the heap
    for i in range(n - 1, 0, -1):
        arr[0], arr[i] = arr[i], arr[0]  # Here we swap the value
        max_heapify(arr, i, 0)  # and we go one and one :)
    return arr
