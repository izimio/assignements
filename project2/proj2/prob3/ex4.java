import java.util.concurrent.*;

public class ex4 {
    public static void main(String[] args) {
        final int parties = 3;
        CyclicBarrier barrier = new CyclicBarrier(parties,
            () -> System.out.println("All threads have reached the barrier. Continuing...")
        );

        Runnable worker = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " waiting at barrier");
                barrier.await();
                System.out.println(Thread.currentThread().getName() + " passed the barrier");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        for (int i = 1; i <= parties; i++) {
            new Thread(worker, "Thread-" + i).start();
        }
    }
}