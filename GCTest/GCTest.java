public class GCTest extends Thread {
    static final int N = 9000000;
    static final int M = 4;
    static GCTest[] g;
    Object[] objs = new Object[N];

	public static void main(String ... arg) throws Exception {
        g = new GCTest[M];
        for (int i = 0 ; i < M ; ++i)
            g[i] = new GCTest();

        System.out.println("warm up ...");
        for (int i = 0 ; i < N ; ++i)
            for (int j = 0 ; j < M ; ++j)
                g[j].doIt(i);

        System.out.println("start");

        for (int j = 0 ; j < M ; ++j)
            g[j].start();
    }

    public void run() {
        long start = System.currentTimeMillis();
        for (int j = 0 ; j < 60 ; ++j)
            for (int i = 0 ; i < N ; ++i)
                doIt(i);
        long end = System.currentTimeMillis();
        System.out.println("time: " + (end-start) + "ms");
    }

    void doIt(int i) {
        if (objs[i] == null)
            objs[i] = new X();
        else
            objs[i] = g[i%M].objs[i];
    }

    static class X {
        byte[] b = new byte[128];
    }
}

/*
java -XX:+PrintFlagsFinal -version | grep -i "HeapSize MetaspaceSize ThreadStackSize"
java -XX:+PrintFlagsFinal -version | grep -iE "HeapSize MetaspaceSize ThreadStackSize"
java -XX:+PrintFlagsFinal -version | grep -iE "MetaspaceSize"

javac GCTest.java 
java -Xlog:gc:gc.txt GCTest
java -Xlog:gc=trace:gc.txt GCTest
java -Xlog:gc=trace:gc.txt -XX:InitiatingHeapOccupancyPercent=10 GCTest
java -Xlog:gc=trace:gc.txt -XX:InitiatingHeapOccupancyPercent=60 GCTest
java -Xlog:gc=trace:gc.txt -XX:InitiatingHeapOccupancyPercent=50 GCTest
java -Xlog:gc=trace:file=gc1.txt -XX:InitiatingHeapOccupancyPercent=50 GCTest
*/
