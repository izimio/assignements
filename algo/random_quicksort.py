def median_of_three(arr, low, high):
    mid = (low + high) // 2
    trio = [(arr[low], low), (arr[mid], mid), (arr[high], high)]
    trio.sort(key=lambda x: x[0])
    return trio[1][1]  # index of the median value calculated above taking the low, mid and highest value ! 


def partition(arr, low, high):
    pivot_index = median_of_three(arr, low, high)
    pivot_value = arr[pivot_index]

    # Move pivot to the end
    arr[pivot_index], arr[high] = arr[high], arr[pivot_index]

    i = low
    for j in range(low, high):
        if arr[j] < pivot_value:
            arr[i], arr[j] = arr[j], arr[i]
            i += 1

    # Place pivot !
    arr[i], arr[high] = arr[high], arr[i]
    return i


def quicksort(arr, low=0, high=None):
    # We create a copy of the array to return it at the end
    if high is None:
        arr = arr[:]
        high = len(arr) - 1

    if low < high:
        pivot = partition(arr, low, high)
        quicksort(arr, low, pivot - 1)
        quicksort(arr, pivot + 1, high)

    return arr  