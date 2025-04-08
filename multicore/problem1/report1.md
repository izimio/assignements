# Problem 1 report

## Environment

## 1. Environment

| Property                     | Value            |
| ---------------------------- | ---------------- |
| Java Version                 | 21               |
| OS Name                      | Linux            |
| OS Version                   | 6.8.0-52-generic |
| Architecture                 | amd64            |
| Available processors (cores) | 8                |
| Max memory (MB)              | 3936             |

> ✅ All compilation and execution were performed inside a Docker container using the image:
> `FROM openjdk:21-slim`

## 🖥 Programs Benchmark

### Reference serial program

| exec time (in ms)            | 1    | 2    | 4    | 6    | 8    | 10   | 12   | 14   | 16   | 32   |
| ---------------------------- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| default serial (prime count) | 2242 | 2362 | 2293 | 2334 | 2344 | 2359 | 2231 | 2399 | 2510 | 2545 |

### ⏱ Execution Time (ms)

![Graph](exec.png)

| exec time (in ms)         | 1    | 2    | 4    | 6   | 8   | 10  | 12  | 14  | 16  | 32  |
| ------------------------- | ---- | ---- | ---- | --- | --- | --- | --- | --- | --- | --- |
| static (block)            | 2429 | 1857 | 1150 | 941 | 905 | 772 | 799 | 846 | 881 | 772 |
| static (cyclic) (size 10) | 2273 | 1243 | 655  | 789 | 664 | 668 | 682 | 735 | 672 | 717 |
| dynamic (size 10)         | 2344 | 1217 | 657  | 648 | 672 | 664 | 663 | 650 | 659 | 670 |

---

### ⚡ Performance (1/ms)

![Graph](performance.png)

| performance (1/s) | 1      | 2      | 4      | 6      | 8      | 10     | 12     | 14     | 16     | 32     |
| ----------------- | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ |
| static (block)    | 0.4327 | 0.5790 | 0.9662 | 1.3175 | 1.2438 | 1.3158 | 1.4144 | 1.2563 | 1.4265 | 1.4245 |
| static (cyclic)   | 0.4361 | 0.8123 | 1.5337 | 1.3986 | 1.5106 | 1.3966 | 1.4124 | 1.3928 | 1.4837 | 1.5106 |
| dynamic (size 10) | 0.4243 | 0.7746 | 1.5221 | 1.5291 | 1.5408 | 1.5038 | 1.5221 | 1.4837 | 1.5015 | 1.5060 |
