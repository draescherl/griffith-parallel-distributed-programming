import java.util.Random;

public class AddExplorer extends Explorer {

    public AddExplorer(CircularQueue<Integer> queue) {
        super(queue);
    }

    @Override
    public void run() {
        Random random = new Random();
        int randomNumber = random.nextInt();
        queue.join(randomNumber);
    }

}
