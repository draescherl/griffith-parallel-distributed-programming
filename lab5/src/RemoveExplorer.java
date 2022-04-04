public class RemoveExplorer extends Explorer {

    public RemoveExplorer(CircularQueue<Integer> queue) {
        super(queue);
    }

    @Override
    public void run() {
        queue.leave();
    }

}
