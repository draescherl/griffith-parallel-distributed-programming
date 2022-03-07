public class Main {

    public static void main(String[] args) throws InterruptedException {

        int viewers = Runtime.getRuntime().availableProcessors();
        Viewer[] viewerPool = new Viewer[viewers];
	    ViewingStand viewingStand = new ViewingStand(4);

        for (int i = 0; i < viewers; i++) viewerPool[i] = new Viewer(viewingStand);
        for (int i = 0; i < viewers; i++) viewerPool[i].start();
        for (int i = 0; i < viewers; i++) viewerPool[i].join();

    }

}
