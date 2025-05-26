#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>
#include <omp.h>

#define SPHERES 20
#define rnd(x) (x * rand() / RAND_MAX)
#define INF 2e10f
#define DIM 2048

struct Sphere {
    float r, g, b;
    float radius;
    float x, y, z;

    float hit(float ox, float oy, float *n) {
        float dx = ox - x;
        float dy = oy - y;
        if (dx * dx + dy * dy < radius * radius) {
            float dz = sqrtf(radius * radius - dx * dx - dy * dy);
            *n = dz / sqrtf(radius * radius);
            return dz + z;
        }
        return -INF;
    }
};

void kernel(int x, int y, Sphere* s, unsigned char* ptr) {
    int offset = x + y * DIM;
    float ox = (x - DIM / 2);
    float oy = (y - DIM / 2);

    float r = 0, g = 0, b = 0;
    float maxz = -INF;

    for (int i = 0; i < SPHERES; i++) {
        float n;
        float t = s[i].hit(ox, oy, &n);
        if (t > maxz) {
            float fscale = n;
            r = s[i].r * fscale;
            g = s[i].g * fscale;
            b = s[i].b * fscale;
            maxz = t;
        }
    }

    // RGB values are clamped to the range [0, 255]
    ptr[offset * 4 + 0] = (int)(r * 255);
    ptr[offset * 4 + 1] = (int)(g * 255);
    ptr[offset * 4 + 2] = (int)(b * 255);
    ptr[offset * 4 + 3] = 255;
}

// Writting PPM files format
void ppm_write(unsigned char* bitmap, int xdim, int ydim, FILE* fp) {
    fprintf(fp, "P3\n%d %d\n255\n", xdim, ydim);
    for (int y = 0; y < ydim; y++) {
        for (int x = 0; x < xdim; x++) {
            int i = x + y * xdim;
            fprintf(fp, "%d %d %d ", bitmap[4 * i], bitmap[4 * i + 1], bitmap[4 * i + 2]);
        }
        fprintf(fp, "\n");
    }
}

int main(int argc, char* argv[]) {
    if (argc != 2) {
        printf("> Usage: %s [number_of_threads]\n", argv[0]);
        return 1;
    }

    int no_threads = atoi(argv[1]);
    omp_set_num_threads(no_threads);

    Sphere* spheres = (Sphere*)malloc(sizeof(Sphere) * SPHERES);
    unsigned char* bitmap = (unsigned char*)malloc(sizeof(unsigned char) * DIM * DIM * 4);

    srand(time(NULL));
    for (int i = 0; i < SPHERES; i++) {
        spheres[i].r = rnd(1.0f);
        spheres[i].g = rnd(1.0f);
        spheres[i].b = rnd(1.0f);
        spheres[i].x = rnd(2000.0f) - 1000;
        spheres[i].y = rnd(2000.0f) - 1000;
        spheres[i].z = rnd(2000.0f) - 1000;
        spheres[i].radius = rnd(200.0f) + 40;
    }

    double start = omp_get_wtime();

    // Process image rendering for each sphere in parallel
    #pragma omp parallel for collapse(2) schedule(dynamic)
    for (int x = 0; x < DIM; x++) {
        for (int y = 0; y < DIM; y++) {
            kernel(x, y, spheres, bitmap);
        }
    }

    double end = omp_get_wtime();
    double elapsed = end - start;

    FILE* fp = fopen("result.ppm", "w");
    if (!fp) {
        fprintf(stderr, "Failed to open file for writing.\n");
        return 1;
    }
    ppm_write(bitmap, DIM, DIM, fp);
    fclose(fp);

    printf("OpenMP (%d threads) ray tracing: %.3f sec\n", no_threads, elapsed);
    printf("[result.ppm] was generated.\n");

    free(bitmap);
    free(spheres);
    return 0;
}
