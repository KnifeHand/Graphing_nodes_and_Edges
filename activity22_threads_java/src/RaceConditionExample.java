public class RaceConditionExample {
    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread incThread = new IncrementThread(counter);
        Thread decThread = new DecrementThread(counter);
        incThread.start();
        decThread.start();
        // wait for both threads to finish
        try {
            incThread.join();
            decThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Counter value is " + counter.value());
    }
}

