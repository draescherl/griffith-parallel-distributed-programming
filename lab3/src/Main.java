import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {

        // Initialize variables
        int threads = Runtime.getRuntime().availableProcessors();
        int freq = 0;
        int[] f = new int[100000000];
        Instant start, finish;
        long timeElapsed;
        ArrayList<Future<Data>> futures = new ArrayList<>();
        Data[] results = new Data[threads];

        // Initialize array and find the size of each block a thread will work on
        for (int i = 0; i < f.length; i++) f[i] = (int)(Math.random() * f.length);
        int max = f[0];
        int blockSize = f.length / threads;

        // Run the program single threaded to get a baseline reading
        System.out.println("Running single-threaded ...");
        start = Instant.now();
        for (int value : f) {
            if (value % 2 == 0) freq++;
            if (value > max) max = value;
        }
        finish = Instant.now();
        timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Frequency of even values found : " + freq + ", max found : " + max + " took " + timeElapsed + "ms.");

        // Run multithreaded
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        System.out.println();
        System.out.println("Running on " + threads + " threads ...");
        freq = 0;
        start = Instant.now();
        // Give workload to the threads
        for (int i = 0; i < threads; i++) {
            Future<Data> future = pool.submit(new Explorer(f, i * blockSize, i * blockSize + blockSize - 1));
            futures.add(future);
        }
        pool.shutdown();
        // Retrieve results
        for (int i = 0; i < threads; i++) {
            Future<Data> future = futures.get(i);
            results[i] = future.get();
        }
        // Combine results
        max = results[0].getMax();
        for (int i = 0; i < threads; i++) {
            freq += results[i].getFrequency();
            int tmpMax = results[i].getMax();
            if (tmpMax > max) max = tmpMax;
        }
        finish = Instant.now();
        timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println("Frequency of even values found : " + freq + ", max found : " + max + " took " + timeElapsed + "ms.");

    }
}
