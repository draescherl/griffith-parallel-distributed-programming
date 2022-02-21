public class Explorer extends Thread {

    private final int[] array;
    private final int startIndex;
    private final int endIndex;
    private int freq;

    public Explorer(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.freq = 0;
    }

    @Override
    public void run() {
        // System.out.println("Thread ID " + Thread.currentThread().getId() + " running on block [" + startIndex + ";" + endIndex + "]");
        for (int i = startIndex; i <= endIndex; i++) if (array[i] % 2 == 0) freq++;
    }

    public int getFreq() {
        return freq;
    }
}
