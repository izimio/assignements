import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

public class ex5 {
    public static void main(String[] args) throws Exception {
        final int RANGE = 200_000;
        final int THREADS = 4;
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);

        int chunkSize = RANGE / THREADS;
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < THREADS; i++) {
            int start = i * chunkSize + 1;
            int end = (i == THREADS - 1) ? RANGE : (i + 1) * chunkSize;
            futures.add(executor.submit(new PrimeCountTask(start, end)));
        }

        int totalPrimes = 0;
        for (Future<Integer> future : futures) {
            totalPrimes += future.get();
        }

        executor.shutdown();
        System.out.println("Total primes between 1 and " + RANGE + ": " + totalPrimes);
    }
}

class PrimeCountTask implements Callable<Integer> {
    private final int start, end;
    public PrimeCountTask(int s, int e) { this.start = s; this.end = e; }

    @Override
    public Integer call() {
        int count = 0;
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                count++;
            }
        }
        return count;
    }
    private boolean isPrime(int n) {
        if (n < 2) return false;
        if (n % 2 == 0) return n == 2;
        int sqrt = (int)Math.sqrt(n);
        for (int i = 3; i <= sqrt; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}