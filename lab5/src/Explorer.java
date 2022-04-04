public abstract class Explorer extends Thread {

    protected CircularQueue<Integer> queue;

    public Explorer(CircularQueue<Integer> queue) {
        this.queue = queue;
    }

}
