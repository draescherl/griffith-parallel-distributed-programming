package message;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        MessagePrinter printer = new MessagePrinter("Repeated message.");
        printer.start();
        Thread.sleep(5000);
        printer.terminate();
    }

}
