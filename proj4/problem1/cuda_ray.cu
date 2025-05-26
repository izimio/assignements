// %%writefile cuda_ray.cu
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <cuda.h>
#include <ctime>

#define SPHERES 20
#define INF 2e10f
#define DIM 2048
#define rnd(x) (x * rand() / RAND_MAX)

struct Sphere
{
    float r, g, b;
    float radius;
    float x, y, z;

    __device__ float hit(float ox, float oy, float *n)
    {
        float dx = ox - x;
        float dy = oy - y;
        if (dx * dx + dy * dy < radius * radius)
        {
            float dz = sqrtf(radius * radius - dx * dx - dy * dy);
            *n = dz / sqrtf(radius * radius);
            return dz + z;
        }
        return -INF;
    }
};

__global__ void kernel(Sphere *s, unsigned char *ptr)
{
    int x = blockIdx.x * blockDim.x + threadIdx.x;
    int y = blockIdx.y * blockDim.y + threadIdx.y;

    if (x >= DIM || y >= DIM)
        return;

    int offset = x + y * DIM;
    float ox = (x - DIM / 2);
    float oy = (y - DIM / 2);

    float r = 0, g = 0, b = 0;
    float maxz = -INF;

    for (int i = 0; i < SPHERES; i++)
    {
        float n;
        float t = s[i].hit(ox, oy, &n);
        if (t > maxz)
        {
            float fscale = n;
            r = s[i].r * fscale;
            g = s[i].g * fscale;
            b = s[i].b * fscale;
            maxz = t;
        }
    }

    ptr[offset * 4 + 0] = (int)(r * 255);
    ptr[offset * 4 + 1] = (int)(g * 255);
    ptr[offset * 4 + 2] = (int)(b * 255);
    ptr[offset * 4 + 3] = 255;
}

void ppm_write(unsigned char *bitmap, int xdim, int ydim, const char *filename)
{
    FILE *fp = fopen(filename, "w");
    if (!fp)
    {
        fprintf(stderr, "Failed to write file.\n");
        return;
    }
    fprintf(fp, "P3\n%d %d\n255\n", xdim, ydim);
    for (int y = 0; y < ydim; y++)
    {
        for (int x = 0; x < xdim; x++)
        {
            int i = x + y * xdim;
            fprintf(fp, "%d %d %d ", bitmap[4 * i], bitmap[4 * i + 1], bitmap[4 * i + 2]);
        }
        fprintf(fp, "\n");
    }
    fclose(fp);
}

int main()
{
    Sphere h_spheres[SPHERES];
    srand(time(NULL));
    for (int i = 0; i < SPHERES; i++)
    {
        h_spheres[i].r = rnd(1.0f);
        h_spheres[i].g = rnd(1.0f);
        h_spheres[i].b = rnd(1.0f);
        h_spheres[i].x = rnd(2000.0f) - 1000;
        h_spheres[i].y = rnd(2000.0f) - 1000;
        h_spheres[i].z = rnd(2000.0f) - 1000;
        h_spheres[i].radius = rnd(200.0f) + 40;
    }

    Sphere *d_spheres;
    unsigned char *d_bitmap;
    unsigned char *h_bitmap = (unsigned char *)malloc(DIM * DIM * 4);

    cudaMalloc(&d_spheres, sizeof(Sphere) * SPHERES);
    cudaMemcpy(d_spheres, h_spheres, sizeof(Sphere) * SPHERES, cudaMemcpyHostToDevice);

    cudaMalloc(&d_bitmap, DIM * DIM * 4);

    dim3 threadsPerBlock(16, 16);
    dim3 numBlocks((DIM + 15) / 16, (DIM + 15) / 16);

    // Start timing
    cudaEvent_t start, stop;
    cudaEventCreate(&start);
    cudaEventCreate(&stop);
    cudaEventRecord(start);

    kernel<<<numBlocks, threadsPerBlock>>>(d_spheres, d_bitmap);
    cudaDeviceSynchronize();

    cudaEventRecord(stop);
    cudaEventSynchronize(stop);

    float milliseconds = 0;
    cudaEventElapsedTime(&milliseconds, start, stop);

    // Copy result back
    cudaMemcpy(h_bitmap, d_bitmap, DIM * DIM * 4, cudaMemcpyDeviceToHost);
    ppm_write(h_bitmap, DIM, DIM, "result.ppm");

    printf("CUDA ray tracing: %.3f sec\n", milliseconds / 1000.0f);
    printf("[result.ppm] was generated.\n");

    // Cleanup
    free(h_bitmap);
    cudaFree(d_bitmap);
    cudaFree(d_spheres);

    return 0;
}
