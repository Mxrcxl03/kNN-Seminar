import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.time.Duration;

public class PerformanceTest {
    public static Duration measureCpuTime(Runnable runnable) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        if (!threadMXBean.isThreadCpuTimeSupported()) {
            throw new UnsupportedOperationException("JVM does not support measuring thread CPU-time");
        }

        final long[] finalCpuTime = {0L};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runnable.run();
                finalCpuTime[0] = threadMXBean.getThreadCpuTime(Thread.currentThread().getId());
            }
        });

        thread.start();

        while (finalCpuTime[0] == 0 && thread.isAlive()) {
            try {
                Thread.sleep(120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (finalCpuTime[0] == 0) {
            throw new RuntimeException("Operation never returned, and the thread is dead (perhaps an unhandled exception occurred)");
        }

        return Duration.ofNanos(finalCpuTime[0]);
    }

    public static void main(String[] args) {
        String id = "id";
        String logi = "logi";
        String tanh = "tanh";
        int[] netzkonf1 = {2, 2};
        double[] input1 = {1, 0};
        int[] netzkonf2 = {100, 100, 101};
        double[] input2 = new double[100];
        double[][][] gewichtsdatei1 = {
                { {-0.081, 0.08, -0.04}, {0.06, 0.02, -0.003}, {-0.01, 0.003, -0.09}, {0.08, -0.09, -0.05} },
                { {-0.008, 0.01, 0.01, 2.9E-4}, {0.06, -0.06, -0.027, -0.01}, {0.04, 0.06, 0.08, 0.08}, {-0.08, 0.06, 0.09, -0.001} }};
        NeuronalesNetz n1 = new NeuronalesNetz();
        double[][][] gewichtsdatei2 = {
                {{0.01, 0,02}, {0.3,-0.9}}
        };

        Duration cpuTime = measureCpuTime(() -> {
            n1.initNetz(netzkonf1);
            n1.initGewichteRandom();

            /*for(int i = 0; i < netzkonf1[1]; i++) {
                n1.layer[1][i].setActivationFunction(logi);
            }*/

            n1.computeNN(input1);
        });

        System.out.println("CPU Time: " + cpuTime.toMillis() + " milliseconds");
    }
}
