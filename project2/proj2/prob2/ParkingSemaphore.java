import java.util.concurrent.Semaphore;

class Constants {
    public static final int MAX_CARS = 10;
    public static final int MAX_PARKING_SPOTS = 7;
}

class ParkingGarage {
    private final Semaphore spots;

    public ParkingGarage(int places) {
        this.spots = new Semaphore(places);
    }

    public void enter(String carName) {
        try {
            spots.acquire();
            System.out.println(carName + ": just entered");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void leave(String carName) {
        spots.release(); 
        System.out.println(carName + ":                                     have been left");
    }
}

class Car extends Thread {
    private final ParkingGarage garage;

    public Car(String name, ParkingGarage garage) {
        super(name);
        this.garage = garage;
    }

    private void tryingEnter() {
        System.out.println(getName() + ": trying to enter");
    }

    private void aboutToLeave() {
        System.out.println(getName() + ":                                     about to leave");
    }

    public void run() {
        while (true) {
            try {
                sleep((int)(Math.random() * 10000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            tryingEnter();
            garage.enter(getName());

            try {
                sleep((int)(Math.random() * 20000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            aboutToLeave();
            garage.leave(getName());
        }
    }
}

public class ParkingSemaphore {
    public static void main(String[] args) {
        ParkingGarage garage = new ParkingGarage(Constants.MAX_PARKING_SPOTS);
        for (int i = 1; i <= Constants.MAX_CARS; i++) {
            new Car("Car " + i, garage).start();
        }
    }
}
