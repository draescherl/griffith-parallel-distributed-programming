package egg;

import java.util.Scanner;

public class EggTimerTest {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        EggTimer et = new EggTimer();
        System.out.println("Press enter to stop egg timer");
        et.start();
        in.nextLine();
        et.terminate();
    }

}
