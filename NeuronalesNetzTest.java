public class NeuronalesNetzTest {
  public static void main(String[] args) {
    String id = "id";
    String logi = "logi";
    String tanh = "tanh";

    double lernrate = 0.01;

    int[] netzkonf1 = {3, 3, 4};
    double[] input1 = {1, 0, 0};

    double[][][] gewichtsdatei1 = {
      { {-0.081, 0.08, -0.04}, {0.06, 0.02, -0.003}, {-0.01, 0.003, -0.09}, {0.08, -0.09, -0.05} },
      { {-0.008, 0.01, 0.01, 2.9E-4}, {0.06, -0.06, -0.027, -0.01}, {0.04, 0.06, 0.08, 0.08}, {-0.08, 0.06, 0.09, -0.001} }};

    double[] inputT1 = {0.8, 0, 0.1};
    double[] idealOutT1 = {1, 0, 0, 0};

    double[] inputT2 = {0.99, 0.1, 0};
    double[] idealOutT2 = {1, 0, 0, 0};

    double[] inputT3 = {1, 1, 0};
    double[] idealOutT3 = {0, 1, 0, 0};

    double[] inputT4 = {0.1, 0.1, 1};
    double[] idealOutT4 = {0, 0, 1, 0};

    double[] inputT5 = {0.1, 1.1, 0};
    double[] idealOutT5 = {0, 0, 0, 1};

    NeuronalesNetz n1 = new NeuronalesNetz();
    n1.initNetz(netzkonf1);
    n1.initGewichte(gewichtsdatei1);

    for(int i = 0; i < netzkonf1[1]; i++) {
      n1.layer[1][i].setActivationFunction(logi);
    }

    n1.computeNN(input1);
    //n1.ausgabeOutput();

    n1.backpropagation(inputT1, idealOutT1, lernrate);
    n1.ausgabeOutput();
    n1.backpropagation(inputT2, idealOutT2, lernrate);
    n1.ausgabeOutput();
    n1.backpropagation(inputT3, idealOutT3, lernrate);
    n1.ausgabeOutput();
    n1.backpropagation(inputT4, idealOutT4, lernrate);
    n1.ausgabeOutput();
    n1.backpropagation(inputT5, idealOutT5, lernrate);

    int[] netzkonf2 = {2, 2, 2};
    double[] input2 = {0.7, 0.6};
    double[] idealOutputI2 = {0.9, 0.2};
    double[][][] gewichtsdatei2 = {
            { {0.8, -0.6}, {0.5, 0.7}, {0.3, -0.2} },
            { {0.4, -0.4}, {0.3, 0.9}, {0.2, 0.1} } };


    NeuronalesNetz n2 = new NeuronalesNetz();
    n2.initNetz(netzkonf2);
    n2.initGewichte(gewichtsdatei2);

    for(int i = 0; i < netzkonf2[1]; i++) {
      n2.layer[1][i].setActivationFunction(logi);
    }

    for(int i = 0; i < netzkonf2[2]; i++) {
      n2.layer[2][i].setActivationFunction(logi);
    }

    n2.computeNN(input2);
    n2.ausgabeOutput();

    //n2.backpropagation(input2, idealOutputI2, lernrate);
    //n2.ausgabeOutput();
  }
}
