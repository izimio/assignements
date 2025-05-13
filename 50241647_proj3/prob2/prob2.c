#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <math.h>

int is_prime(int n)
{
    if (n < 2)
        return 0;
    int i;
    for (i = 2; i <= sqrt(n); i++)
    {
        if (n % i == 0)
            return 0;
    }
    return 1;
}

int main(int argc, char *argv[])
{
    if (argc != 3)
    {
        printf("Usage: %s scheduling_type thread_count\n", argv[0]);
        printf("scheduling_type: 1=static, 2=dynamic, 3=static,10, 4=dynamic,10\n");
        return 1;
    }

    int scheduling_type = atoi(argv[1]);
    int num_threads = atoi(argv[2]);
    int max_num = 200000;
    int total_primes = 0;

    double start_time = omp_get_wtime();

    omp_set_num_threads(num_threads);

#pragma omp parallel
    {
        int local_count = 0;

#pragma omp for schedule(static) nowait
        for (int i = 2; i <= 2; i++)
        {
        } // empty to "warm-up" threads

#pragma omp for reduction(+ : total_primes) \
    schedule(static)
        for (int i = 2; i <= max_num; i++)
        {
            if (is_prime(i))
            {
                total_primes++;
            }
        }
    }

    double end_time = omp_get_wtime();
    double exec_time = (end_time - start_time) * 1000;

    printf("Threads: %d\n", num_threads);
    printf("Scheduling Type: %d\n", scheduling_type);
    printf("Total Primes: %d\n", total_primes);
    printf("Execution Time (ms): %.3f\n", exec_time);

    return 0;
}
