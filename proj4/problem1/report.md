## 🧰 Compilation & Execution

Compilation has been made on a Linux Ubuntu 22.04.3 LTS

## 1. Environment

```

| **Propriété**                   | **Valeur**               |
|---------------------------------|--------------------------|
| OS Name                         | Linux                    |
| OS Version                      | 6.8.0-52-generic         |
| Architecture                    | amd64                    |
| Available processors (cores)    | 8                        |
| Max memory (MB)                 | 3936                     |

```

## 2. Compilation

### 2.1 OpenMP

```bash
g++ -fopenmp openmp_ray.cpp
./a.out <number of threads>
```

### 2.2 CUDA

```bash

```

### exec time (unit: seconds)

| **Type** | 1     | 4     | 8     | 16    |
| -------- | ----- | ----- | ----- | ----- |
| OpenMP   | 1.352 | 0.452 | 0.428 | 0.386 |
| CUDA     |
