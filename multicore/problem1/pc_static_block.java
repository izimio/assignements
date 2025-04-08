import java.util.concurrent.locks.ReentrantLock;

public class pc_static_block {

    private static int NUM_END = 200000;
    private static int NUM_THREAD = 1;

    private static int counter = 0;
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 2) {
            NUM_THREAD = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }

        Thread[] workers = new Thread[NUM_THREAD];
        long globalStart = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREAD; i++) {
            final int threadId = i;
            final int startRange = threadId * NUM_END / NUM_THREAD;
            final int endRange = (threadId + 1) * NUM_END / NUM_THREAD - 1;

            workers[i] = new Thread(() -> {
                // System.out.println("→ Thread-" + threadId + " scanning range: " + startRange + " to " + endRange);
                long localStart = System.currentTimeMillis();

                int localCount = 0;
                for (int num = startRange; num <= endRange; num++) {
                    if (isPrime(num)) {
                        localCount++;
                    }
                }

                lock.lock();
                try {
                    counter += localCount;
                } finally {
                    lock.unlock();
                }

                long localEnd = System.currentTimeMillis();
                long threadDuration = localEnd - localStart;
                // System.out.println("✓ Thread-" + threadId + " done in " + threadDuration + " ms");
            });

            workers[i].start();
        }

        for (Thread t : workers) {
            t.join();
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
