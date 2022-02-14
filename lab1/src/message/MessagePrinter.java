package message;

public class MessagePrinter extends Thread {

    private String message;
    private volatile boolean go = true;

    public MessagePrinter(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        while (this.go) {
            try {
                sleep(100);
            } catch (InterruptedException ignored) {}
            System.out.println(message);
        }
    }

    public void terminate() {
        go = false;
    }

}
