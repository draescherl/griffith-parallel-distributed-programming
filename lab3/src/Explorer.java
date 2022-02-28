import java.util.concurrent.Callable;

public class Explorer implements Callable<Data> {

    private final int[] array;
    private final int lbound;
    private final int ubound;

    private int max;
    private int freq = 0;
    private final Data data = new Data();

    public Explorer(int[] array, int lbound, int ubound) {
        this.array = array;
        this.lbound = lbound;
        this.ubound = ubound;
    }

    private void findValues() {
        for (int i = lbound; i <= ubound; i++) {
            if (array[i] % 2 == 0) freq++;
            if (array[i] > max) max = array[i];
        }
        data.setFrequency(freq);
        data.setMax(max);
    }

    @Override
    public Data call() throws Exception {
        findValues();
        return this.data;
    }

}
