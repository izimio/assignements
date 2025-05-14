#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

const int SEQUENTIAL_CUTOFF = 1000;

long divide_and_conquer_sum(long *arr, int lo, int hi)
{
    if ((hi - lo) < SEQUENTIAL_CUTOFF)
    {
        long sum = 0;
        for (int i = lo; i < hi; i++)
        {
            sum += arr[i];
        }
        return sum;
    }
    else
    {
        long l_sum = 0, r_sum = 0;

#pragma omp task shared(l_sum)
        {
            l_sum = divide_and_conquer_sum(arr, lo, (lo + hi) / 2);
        }

#pragma omp task shared(r_sum)
        {
            r_sum = divide_and_conquer_sum(arr, (lo + hi) / 2, hi);
        }

#pragma omp taskwait
        long total = l_sum + r_sum;
        printf("        sum(%d to %d) = %ld\n", lo, hi - 1, total);
        return total;
    }
}

int main(int ac, char *av[])
{
    int NUM_END = 10000;
    if (ac == 2)
    {
        NUM_END = atoi(av[1]);
    }

    long *arr;
    if ((arr = malloc(NUM_END * sizeof(long))) == NULL || NUM_END < 1)
    {
        fprintf(stderr, "Memory allocation failed or invalid size\n");
        return 1;
    }

    for (int i = 0; i < NUM_END; i++)
    {
        arr[i] = i + 1;
    }

    long total_sum;

#pragma omp parallel
    {
#pragma omp single
        {
            total_sum = divide_and_conquer_sum(arr, 0, NUM_END);
        }
    }

    printf("sum from 1 to %d =\n%ld\n", NUM_END, total_sum);
    free(arr);
    return 0;
}
