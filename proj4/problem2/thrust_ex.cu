% % writefile thrust_ex.cu
#include <thrust/count.h>
#include <thrust/transform_reduce.h>
#include <thrust/execution_policy.h>
#include <iostream>
#include <chrono>

    // Reference to my math teacher in high school, Mr. Berthelot, who taught us the beauty of monte carlo methods ^^
    struct BerthelotPiKernel
{
    double dx;

    __host__ __device__
    BerthelotPiKernel(double interval) : dx(interval) {}

    __host__ __device__ double operator()(long long idx) const
    {
        double midpoint = (idx + 0.5) * dx;
        return 4.0 / (1.0 + midpoint * midpoint);
    }
};

int main()
{
    constexpr long long totalSlices = 1'000'000'000LL;
    const double delta = 1.0 / static_cast<double>(totalSlices);

    // We start the timing refrences here
    auto tic = std::chrono::high_resolution_clock::now();

    // reducing the pi calculus
    double sum = thrust::transform_reduce(
        thrust::device,
        thrust::make_counting_iterator(0LL),
        thrust::make_counting_iterator(totalSlices),
        BerthelotPiKernel(delta),
        0.0,
        thrust::plus<double>());

    double piApprox = sum * delta;

    auto toc = std::chrono::high_resolution_clock::now();
    double duration = std::chrono::duration<double>(toc - tic).count();

    // logging our results
    std::cout.precision(12);
    std::cout << "[GPU] Execution Time: " << duration << " sec\n";
    std::cout << "[GPU] Approximation of π : " << piApprox << "\n";

    return 0;
}
