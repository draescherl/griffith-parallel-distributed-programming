import java.time.Duration;
import java.time.Instant;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        // Initialize variables
        int threads = Runtime.getRuntime().availableProcessors();
        int freq = 0;
        int[] f = new int[1000000];
        Instant start, finish;
        long timeElapsed;
        Explorer[] threadPool = new Explorer[threads];

        // Initialize array and find the size of each block a thread will work on
        for (int i = 0; i < f.length; i++) f[i] = (int)(Math.random() * 100000);
        int blockSize = f.length / threads;

        // Run the program single threaded to get a baseline reading
        System.out.println("Running single-threaded ...");
        start = Instant.now();
        for (int value : f) if (value % 2 == 0) freq++;
        finish = Instant.now();
        timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Frequency of even values found : " + freq + " took " + timeElapsed + "ms.");

        // Run multithreaded
        System.out.println();
        System.out.println("Running on " + threads + " threads ...");
        freq = 0;
        start = Instant.now();
        for (int i = 0; i < threads; i++) threadPool[i] = new Explorer(f, i * blockSize, i * blockSize + blockSize - 1);
        for (int i = 0; i < threads; i++) threadPool[i].start();
        for (int i = 0; i < threads; i++) threadPool[i].join();
        for (int i = 0; i < threads; i++) freq += threadPool[i].getFreq();
        finish = Instant.now();
        timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Frequency of even values found : " + freq + " took " + timeElapsed + "ms.");

    }

}
