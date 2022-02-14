package prime;

public class PrimePrinter extends Thread {

    private volatile boolean go = true;

    @Override
    public void run() {
        long prime = 1;
        long tmp = 2;
        boolean print = true;

        while (go) {
            while (print && tmp < prime) {
                print = !(prime % tmp == 0);
                tmp++;
            }
            tmp = 2;

            if (print) System.out.print(prime + " ");
            prime++;
            print = true;
            try {
                sleep(500);
            } catch (InterruptedException ignored) {}
        }
    }

    public void terminate() {
        this.go = false;
    }

}
