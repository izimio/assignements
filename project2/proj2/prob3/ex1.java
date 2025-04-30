import java.util.concurrent.*;

public class ex1 {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println("Producer putting: " + i);
                    queue.put(i);
                    System.out.println("Producer put: " + i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    int item = queue.take();
                    System.out.println("Consumer took: " + item);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
    }
}