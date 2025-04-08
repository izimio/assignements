import java.util.concurrent.atomic.AtomicInteger;

public class pc_dynamique {
    private static int NUM_END = 200000;
    private static int NUM_THREAD = 1;
    private static final int TASK_SIZE = 10;

    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final AtomicInteger nextTask = new AtomicInteger(1);

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 2) {
            NUM_THREAD = Integer.parseInt(args[0]);
            NUM_END = Integer.parseInt(args[1]);
        }

        Thread[] threads = new Thread[NUM_THREAD];
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < NUM_THREAD; i++) {
            threads[i] = new Thread(() -> {
                while (true) {
                    int start = nextTask.getAndAdd(TASK_SIZE);
                    if (start > NUM_END) break;

                    int end = Math.min(start + TASK_SIZE - 1, NUM_END);

                    for (int j = start; j <= end; j++) {
                        if (isPrime(j)) {
                            counter.incrementAndGet();
                        }
                    }
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        double performance = 1.0 / totalTime;

        System.out.println("=== Total Execution Time: " + totalTime + " ms");
        System.out.println("=== Performance: " + performance + " E-4");
        System.out.println("=== Total Prime Count: " + counter.get());
    }

    private static boolean isPrime(int x) {
        if (x <= 1) return false;
        for (int i = 2; i < x; i++) {
            if (x % i == 0) return false;
        }
        return true;
    }
}
