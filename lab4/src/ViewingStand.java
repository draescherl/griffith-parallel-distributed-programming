import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ViewingStand {

    private final boolean[] seats;
    private int available;
    private final Lock lock;
    private final Condition seat;

    public ViewingStand(int numSeats) {
        this.seats = new boolean[numSeats];
        Arrays.fill(seats, true);
        this.available = numSeats;
        this.lock = new ReentrantLock();
        this.seat = lock.newCondition();
    }

    public int findSeat() {
        lock.lock();
        try {
            while (this.available == 0) {
                try {
                    seat.await();
                } catch (InterruptedException ignored) {}
            }
            int i = 0;
            while (!seats[i]) i++;
            seats[i] = false;
            available--;
            return i;
        } finally {
            lock.unlock();
        }
    }

    public void leaveSeat(int i) {
        lock.lock();
        try {
            seats[i] = true;
            available++;
            seat.signal();
        } finally {
            lock.unlock();
        }
    }

}
