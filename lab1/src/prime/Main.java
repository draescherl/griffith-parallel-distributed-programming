package prime;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PrimePrinter printer = new PrimePrinter();
        System.out.println("Press enter to stop prime counter");
        printer.start();
        scanner.nextLine();
        printer.terminate();
    }

}
