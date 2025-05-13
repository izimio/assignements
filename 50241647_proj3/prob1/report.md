## 🧰 Compilation & Execution

```bash
gcc -fopenmp -O2 prob1.c -lm
```

Compilation has been made on a Linux Ubuntu 22.04.3 LTS system using the -02 optimization flag.

## 2. Environment

```

| **Propriété**                   | **Valeur**               |
|---------------------------------|--------------------------|
| Java Version                    | 21                       |
| OS Name                         | Linux                    |
| OS Version                      | 6.8.0-52-generic         |
| Architecture                    | amd64                    |
| Available processors (cores)    | 8                        |
| Max memory (MB)                 | 3936                     |

```

## 3. Results

### [REF] exec time (unit: ms)

| **Scheduling** | **Chunk size** | 1      |
| -------------- | -------------- | ------ |
| None           | None           | 42.008 |

### [REF] Performance (1 / exec time)

| **Scheduling** | **Chunk size** | 1     |
| -------------- | -------------- | ----- |
| None           | None           | 0.023 |

## Using OpenMP

### exec time (unit: ms)

| **Scheduling** | **Chunk size** | 1      | 2      | 4      | 6      | 8      | 10     | 12     | 14     | 16     |
| -------------- | -------------- | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ |
| static         | default        | 47.050 | 33.371 | 28.828 | 27.480 | 25.877 | 22.054 | 17.364 | 20.796 | 21.502 |
| dynamic        | default        | 45.403 | 32.600 | 29.843 | 35.035 | 32.867 | 26.588 | 22.836 | 18.603 | 19.007 |
| static         | 10             | 46.342 | 39.619 | 22.122 | 31.914 | 29.079 | 17.740 | 21.084 | 20.375 | 19.017 |
| dynamic        | 10             | 47.000 | 40.733 | 27.913 | 22.369 | 26.388 | 21.925 | 19.518 | 18.699 | 21.066 |

### Performance (1 / exec time)

| **Scheduling** | **Chunk size** | 1     | 2     | 4     | 6     | 8     | 10    | 12    | 14    | 16    |
| -------------- | -------------- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- | ----- |
| static         | default        | 0.021 | 0.030 | 0.035 | 0.036 | 0.039 | 0.045 | 0.058 | 0.048 | 0.047 |
| dynamic        | default        | 0.002 | 0.031 | 0.034 | 0.029 | 0.030 | 0.038 | 0.044 | 0.054 | 0.053 |
| static         | 10             | 0.022 | 0.025 | 0.045 | 0.031 | 0.034 | 0.056 | 0.047 | 0.049 | 0.053 |
| dynamic        | 10             | 0.021 | 0.025 | 0.036 | 0.045 | 0.038 | 0.046 | 0.051 | 0.053 | 0.047 |
