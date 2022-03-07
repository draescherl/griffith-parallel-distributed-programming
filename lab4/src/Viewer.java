public class Viewer extends Thread {

    private final ViewingStand viewingStand;
    private final long waitTime;

    public Viewer(ViewingStand viewingStand) {
        this.viewingStand = viewingStand;
        long leftLimit = 5L, rightLimit = 10L;
        this.waitTime = (leftLimit + (long) (Math.random() * (rightLimit - leftLimit))) * 1000;
    }

    @Override
    public void run() {
        System.out.printf("Viewer %d looking for a seat.%n", Thread.currentThread().getId());
        int seatId = viewingStand.findSeat();
        System.out.printf("Viewer %d found seat %d, waiting for %dms.%n", Thread.currentThread().getId(), seatId, waitTime);
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException ignored) {}
        viewingStand.leaveSeat(seatId);
        System.out.printf("Viewer %d left seat %d.%n", Thread.currentThread().getId(), seatId);

    }

}
