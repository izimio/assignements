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

### ⏱ Execution Time (ms)

| exec time (in ms)         | 1    | 2    | 4    | 6    | 8    | 10   | 12   | 14   | 16   | 32   |
| ------------------------- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| static (block)            | 3703 | 2434 | 1600 | 1587 | 1280 | 1208 | 1264 | 1528 | 1305 | 1266 |
| static (cyclic) (size 10) | 3329 | 1766 | 1103 | 1267 | 1088 | 1215 | 1083 | 1020 | 1114 | 1083 |
| dynamic (size 10)         | 7576 | 3778 | 1920 | 1759 | 1747 | 1776 | 1950 | 1825 | 2059 | 1804 |

---

### ⚡ Performance (1/ms)

| performance (1/ms) (E-4) | 1    | 2    | 4    | 6    | 8    | 10   | 12   | 14   | 16   | 32   |
| ------------------------ | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| static (block)           | 3.00 | 5.66 | 9.07 | 7.89 | 9.19 | 8.23 | 9.23 | 9.80 | 8.98 | 9.23 |
| static (cyclic)          | 2.70 | 4.10 | 6.25 | 6.30 | 7.81 | 8.28 | 7.91 | 6.54 | 7.66 | 7.89 |
| dynamic (size 10)        | 1.32 | 2.65 | 5.21 | 5.69 | 5.72 | 5.63 | 5.13 | 5.48 | 4.86 | 5.54 |
