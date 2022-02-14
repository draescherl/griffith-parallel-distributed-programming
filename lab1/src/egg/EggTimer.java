package egg;

public class EggTimer extends Thread {
    private int et = 0;
    private volatile boolean go = true;

    public void run() {
        while (et < 30 && go) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {

            }
            et++;
            System.out.print(et + " ");
        }
    }

    public void terminate() {
        go = false;
    }
}
