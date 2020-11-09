public class DecrementThread extends Thread {
    private Counter counter;
    public DecrementThread(Counter counter) {
        this.counter = counter;
    }
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            counter.decrement();
        }
    }
}