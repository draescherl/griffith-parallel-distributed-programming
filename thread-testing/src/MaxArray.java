public class MaxArray extends Thread {

    private final int startIndex;
    private final int endIndex;
    private int max;
    private final int[] array;

    public MaxArray(int[] array, int a, int b) {
        this.array = array;
        this.startIndex = a;
        this.endIndex = b;
    }

    public int getMax() {
        return max;
    }

    @Override
    public void run() {
        max = this.array[this.startIndex];
        for (int i = (this.startIndex + 1); i <= this.endIndex; i++) {
            if (this.array[i] > this.max) this.max = this.array[i];
        }
    }

}
