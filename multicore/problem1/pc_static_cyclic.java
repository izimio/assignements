import java.util.concurrent.locks.ReentrantLock;

public class pc_static_cyclic {
    private static int NUM_END = 200000;   // Default input
    private static int NUM_THREADS = 1;    // Default number of threads
    private static final int TASK_SIZE = 10; // Fixed task size

    private static int counter = 0;
    private static final ReentrantLock mutex = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 2) {
            NUM_THREADS = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }

        Thread[] threads = new Thread[NUM_THREADS];

        long programStartTime = System.currentTimeMillis();

        for (int t = 0; t < NUM_THREADS; t++) {
            final int threadId = t;

            threads[t] = new Thread(() -> {
                long startTime = System.currentTimeMillis();

                for (int i = threadId * TASK_SIZE; i < NUM_END; i += TASK_SIZE * NUM_THREADS) {
                    int start = i;
                    int end = Math.min(i + TASK_SIZE, NUM_END);

                    for (int j = start; j < end; j++) {
                        if (isPrime(j)) {
                            mutex.lock();
                            try {
                                counter++;
                            } finally {
                                mutex.unlock();
                            }
                        }
                    }
                }

                long endTime = System.currentTimeMillis();
                System.out.println("Thread " + threadId + " finished in " + (endTime - startTime) + " ms");
            });

            threads[t].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long programEndTime = System.currentTimeMillis();
        long totalTime = programEndTime - programStartTime;

        System.out.println("Program Execution Time: " + totalTime + " ms");
        System.out.println("1.." + NUM_END + " prime counter= " + counter);
    }

    private static boolean isPrime(int x) {
        int i;
        if (x <= 1) return false;
        for (i = 2; i < x; i++) {
            if (x % i == 0) return false;
        }
        return true;
    }
}
