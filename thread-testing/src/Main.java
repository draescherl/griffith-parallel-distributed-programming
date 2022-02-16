import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class Main {

    private static int[] initArray() {
        int[] array = new int[100000000];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(5000);
        }
        return array;
    }

    public static void main(String[] args) throws InterruptedException {

        int[] array = initArray();
        Instant start, finish;
        long timeElapsed;
        int max;

        System.out.println("Finding max with one thread ...");
        MaxArray thread = new MaxArray(array, 0, array.length - 1);
        start = Instant.now();
        thread.start();
        thread.join();
        finish = Instant.now();
        max = thread.getMax();
        timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println(MessageFormat.format("Max found: {0}, took {1} ms.", max, timeElapsed));

        MaxArray thread1 = new MaxArray(array, 0, array.length / 2);
        MaxArray thread2 = new MaxArray(array, array.length / 2 + 1, array.length - 1);
        System.out.println("Finding max with two threads ...");
        start = Instant.now();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        finish = Instant.now();
        max = Math.max(thread1.getMax(), thread2.getMax());
        timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println(MessageFormat.format("Max found: {0}, took {1} ms.", max, timeElapsed));

    }
}
