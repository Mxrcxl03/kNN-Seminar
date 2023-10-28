public class PerformanceTest2 {
    public static void main(String[] args) {
        String id = "id";
        String logi = "logi";
        String tanh = "tanh";

        int[] netzkonf1 = {3, 3, 4};
        double[] input1 = {1, 0, 0};

        double[][][] gewichtsdatei1 = {
                {{-0.081, 0.08, -0.04}, {0.06, 0.02, -0.003}, {-0.01, 0.003, -0.09}, {0.08, -0.09, -0.05}},
                {{-0.008, 0.01, 0.01, 2.9E-4}, {0.06, -0.06, -0.027, -0.01}, {0.04, 0.06, 0.08, 0.08}, {-0.08, 0.06, 0.09, -0.001}}};

        NeuronalesNetz n1 = new NeuronalesNetz();
        n1.initNetz(netzkonf1);
        n1.initGewichte(gewichtsdatei1);

        for (int i = 0; i < netzkonf1[1]; i++) {
            n1.layer[1][i].setActivationFunction(logi);
        }

        n1.computeNN(input1);
    }
}
