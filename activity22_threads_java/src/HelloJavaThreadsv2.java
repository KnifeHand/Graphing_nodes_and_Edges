public class HelloJavaThreadsv2 {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(name + ": " + i);
                }
            }
        };
        Thread t1 = new Thread(runnable);
        t1.setName("t1");
        Thread t2 = new Thread(runnable);
        t2.setName("t2");
        t1.start();
        t2.start();
    }
}
