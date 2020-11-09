public class HelloJavaThreadsv1 {
    public static void main(String[] args) {
        Thread t1 = new MyThread();
        t1.setName("t1");
        Thread t2 = new MyThread();
        t2.setName("t2");
        // the start method call begins the execution of a thread
        t1.start();
        t2.start();
    }
}
