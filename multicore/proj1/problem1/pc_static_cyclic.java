import java.util.concurrent.locks.ReentrantLock;

public class pc_static_cyclic {
    private static int NUM_END = 200000;
    private static int NUM_THREAD = 1;
    private static final int TASK_SIZE = 10;

    private static int counter = 0;
    private static final ReentrantLock mutex = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 2) {
            NUM_THREAD = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }

        Thread[] workers = new Thread[NUM_THREAD];
        long globalStart = System.currentTimeMillis();

        for (int t = 0; t < NUM_THREAD; t++) {
            final int threadId = t;

            workers[t] = new Thread(() -> {
                long localStart = System.currentTimeMillis();
                System.out.println("Thread " + threadId + ": Active");

                for (int i = threadId * TASK_SIZE; i < NUM_END; i += TASK_SIZE * NUM_THREAD) {
                    int rangeStart = i;
                    int rangeEnd = Math.min(i + TASK_SIZE - 1, NUM_END - 1);

                    for (int n = rangeStart; n <= rangeEnd; n++) {
                        if (isPrime(n)) {
                            mutex.lock();
                            try {
                                counter++;
                            } finally {
                                mutex.unlock();
                            }
                        }
                    }
                }

                long localEnd = System.currentTimeMillis();
                System.out.println("Thread " + threadId + ": Done in " + (localEnd - localStart) + "ms");
            });

            workers[t].start();
        }

        for (Thread thread : workers) {
            thread.join();
        }

        long globalEnd = System.currentTimeMillis();
        long totalTime = globalEnd - globalStart;
        
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
