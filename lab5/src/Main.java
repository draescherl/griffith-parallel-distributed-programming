import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        int input;
        CircularQueue<Integer> queue = new CircularQueue<>();

        System.out.println("1) Create a circular queue.");
        System.out.println("2) Create 10 threads that add an element onto the circular queue and 10 threads that take an element off the circular queue. Start both threads at the same time.");
        System.out.println("3) Clear circular queue.");
        System.out.println("99) Exit.");
        System.out.println();

        do {
            System.out.print("Input : ");
            input = scanner.nextInt();

            switch (input) {
                case 1 -> queue = new CircularQueue<>(15);
                case 2 -> {
                    short numberOfThreads = 20;
                    Explorer[] threads = new Explorer[numberOfThreads * 2];
                    for (int i = 0; i < threads.length; i++) {
                        if (i % 2 == 0) threads[i] = new AddExplorer(queue);
                        else threads[i] = new RemoveExplorer(queue);
                    }
                    for (Explorer thread : threads) thread.start();
                    for (Explorer thread : threads) thread.join();
                }
                case 3 -> queue.empty();
            }
        } while (input != 99);

    }

}
