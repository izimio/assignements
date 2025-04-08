THRESHOLD = 50
# Below this size, use insertion sort, it's totally arbitrary
# and can be adjusted for performance tuning


def insertion_sort(arr, start, end):
    for i in range(start + 1, end + 1):
        current = arr[i]
        j = i - 1
        while j >= start and arr[j] > current:
            arr[j + 1] = arr[j]
            j -= 1
        arr[j + 1] = current


def merge(arr, start, mid, end):
    left_half = arr[start:mid + 1]
    right_half = arr[mid + 1:end + 1]

    i = j = 0
    k = start

    while i < len(left_half) and j < len(right_half):
        if left_half[i] <= right_half[j]:
            arr[k] = left_half[i]
            i += 1
        else:
            arr[k] = right_half[j]
            j += 1
        k += 1

    while i < len(left_half):
        arr[k] = left_half[i]
        i += 1
        k += 1

    while j < len(right_half):
        arr[k] = right_half[j]
        j += 1
        k += 1


def merge_insertion_sort(arr, start=0, end=None):
    if end is None:
        end = len(arr) - 1

    if end - start + 1 <= THRESHOLD:
        insertion_sort(arr, start, end)
        return arr

    mid = (start + end) // 2
    merge_insertion_sort(arr, start, mid)
    merge_insertion_sort(arr, mid + 1, end)
    merge(arr, start, mid, end)
    
    # check condition to return the final sorted array
    if start == 0 and end == len(arr) - 1:
        return arr
