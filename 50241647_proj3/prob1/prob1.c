#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <omp.h>

const int MAX_NUM = 200000;

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

void print_help()
{
    printf("Usage: %s scheduling_type thread_count\n", __FILE__);
    printf("scheduling_type: 1=static, 2=dynamic, 3=static,10, 4=dynamic,10\n");
}

int main(int argc, char *argv[])
{
    if (argc != 3)
    {
        print_help();
        return 1;
    }

    int scheduling_type = atoi(argv[1]);
    int num_threads = atoi(argv[2]);
    int total_primes = 0;

    char *sched_env;
    switch (scheduling_type)
    {
    case 1:
        setenv("OMP_SCHEDULE", "static", 1);
        break;
    case 2:
        setenv("OMP_SCHEDULE", "dynamic", 1);
        break;
    case 3:
        setenv("OMP_SCHEDULE", "static,10", 1);
        break;
    case 4:
        setenv("OMP_SCHEDULE", "dynamic,10", 1);
        break;
    default:
        printf("Invalid scheduling_type. Must be 1 to 4.\n");
        return 1;
    }
    omp_set_num_threads(num_threads);

    double start_time = omp_get_wtime();

#pragma omp parallel
    {
#pragma omp for schedule(runtime) reduction(+ : total_primes)
        for (int i = 2; i <= MAX_NUM; i++)
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
    printf("Scheduling Type: %d (%s)\n", scheduling_type, getenv("OMP_SCHEDULE"));
    printf("Execution Time (ms): %.3f\n", exec_time);
    printf("Performance (1/execution time): %.3f\n", (1 / exec_time));

    return 0;
}
