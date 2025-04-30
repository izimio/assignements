import java.util.concurrent.locks.*;

class SharedData {
    private int value = 0;
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    // Write
    public void write(int newValue) {
        rwLock.writeLock().lock();
        try {
            value = newValue;
            System.out.println(Thread.currentThread().getName() + " wrote " + newValue);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    // Reade
    public int read() {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " read " + value);
            return value;
        } finally {
            rwLock.readLock().unlock();
        }
    }
}

public class ex2 {
    public static void main(String[] args) throws InterruptedException {
        SharedData data = new SharedData();

        Thread writer = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                data.write(i);
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }, "Writer-Thread");

        Thread reader1 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                data.read();
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }, "Reader-Thread-1");

        Thread reader2 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                data.read();
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        }, "Reader-Thread-2");

        writer.start();
        reader1.start();
        reader2.start();
        writer.join();
        reader1.join();
        reader2.join();
    }
}