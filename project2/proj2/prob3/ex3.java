import java.util.concurrent.atomic.AtomicInteger;

public class ex3 {
    public static void main(String[] args) {
        AtomicInteger ai = new AtomicInteger(0);
        System.out.println("Initial value: " + ai.get());

        ai.set(5);
        System.out.println("Value after set(5): " + ai.get());

        int oldValue = ai.getAndAdd(10);
        System.out.println("getAndAdd(10) returned: " + oldValue + ", new value: " + ai.get());

        int newValue = ai.addAndGet(3);
        System.out.println("addAndGet(3) returned: " + newValue + ", new value: " + ai.get());
    }
}