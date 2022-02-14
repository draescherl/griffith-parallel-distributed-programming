package coin;

import java.util.Random;

public class CoinTosser extends Thread {

    private int heads = 0;
    private int tails = 0;

    private double headsFreq = 0;
    private double tailsFreq = 0;

    private void toss() {
        Random rand = new Random();
        int flips = 1000;
        for (int i = 0; i < flips; i++) {
            if (rand.nextInt(2) == 0) {
                heads++;
            } else {
                tails++;
            }
        }
    }

    private void computeFrequencies() {
        headsFreq = heads / 1000.0 * 100.0;
        tailsFreq = tails / 1000.0 * 100.0;
    }

    @Override
    public void run() {
        this.toss();
        this.computeFrequencies();
        System.out.println("Heads frequency : " + headsFreq);
        System.out.println("Tails frequency : " + tailsFreq);
    }

}
