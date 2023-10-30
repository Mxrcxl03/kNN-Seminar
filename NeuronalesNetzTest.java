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
    n1.ausgabeOutput();

    n1.backpropagation(inputT1, idealOutT1, lernrate);
    n1.ausgabeOutput();
    n1.backpropagation(inputT2, idealOutT2, lernrate);
    n1.ausgabeOutput();
    n1.backpropagation(inputT3, idealOutT3, lernrate);
    n1.ausgabeOutput();
    n1.backpropagation(inputT4, idealOutT4, lernrate);
    n1.ausgabeOutput();
    n1.backpropagation(inputT5, idealOutT5, lernrate);

    /*
    double[] input2 = {2.0, 1.0, 1.1, -3, 5};
    double[] input3 = {5.0, 2.0, 3.0, 2.0}; // Für netzkonf3 und netzkonf4

    int[] netzkonf1 = {5, 2, 2};
    int[] netzkonf2 = {5, 8, 4, 2};
    int[] netzkonf3 = {4, 1};
    int[] netzkonf4 = {4, 2};

    double[][][] gewichte3 = {{{0.5, 0.2}, {-0.3, 0.1}, {0.1, 0.7}, {-0.6, 0.3}, {0.5, 0.2}, {-0.3, 0.1}},{{0.5, 0.2}, {-0.2, 0.7}, {-0.3, 0.1}}};

    Neuron neuron1;
    Neuron neuron2;
    Neuron neuron3;
    Neuron neuron4;

    NeuronalesNetz nn1 = new NeuronalesNetz();
    nn1.initNetz(netzkonf1);
    nn1.initGewichteRandom();
    nn1.computeNN(input1);

    System.out.println(nn1.getLayerCount()); // 3
    System.out.println(nn1.layer[0][1].getValue());
    System.out.println(nn1.layer[1][6].getValue());
    System.out.println(nn1.layer[2][1].getValue());

    // Ausgabe der einzelnen NIDs testen
    neuron1 = nn1.layer[0][1];
    neuron2 = nn1.layer[1][1];
    neuron3 = nn1.layer[2][1];
    System.out.println(neuron1.getNID()); // Input Neutron 1
    System.out.println(neuron2.getNID()); // Hidden Neutron 1
    System.out.println(neuron3.getNID()); // Output Neutron 1


    nn1.initGewichteRandom();
    for (int i = 0; i < 2; i++) {
      System.out.println("Von Layer " + i + " zu " + (i+1));
      for (int j = 0; j < netzkonf1[i]; j++) {
        for (int k = 0; k < netzkonf1[i+1]; k++) {
          System.out.println(nn1.weights[i][j][k]);
        }
      }
      System.out.println("");
    }

    NeuronalesNetz nn2 = new NeuronalesNetz();
    nn2.initNetz(netzkonf2);
    neuron1 = nn2.layer[0][2];
    neuron2 = nn2.layer[1][6];
    neuron3 = nn2.layer[2][3];
    neuron4 = nn2.layer[3][1];
    System.out.println(neuron1.getNID()); // 2
    System.out.println(neuron2.getNID()); // 6
    System.out.println(neuron3.getNID()); // 3
    System.out.println(neuron4.getNID()); // 1

    NeuronalesNetz nn3 = new NeuronalesNetz();
    nn3.initNetz(netzkonf3);
    nn3.initGewichte(gewichte1);
    nn3.computeNN(input2);

    NeuronalesNetz nn4 = new NeuronalesNetz();
    nn4.initNetz(netzkonf4);
    nn4.initGewichte(gewichte2);
    nn4.computeNN(input2);

    //Fehler absichtlich verursacht
    int[] netzkonf5 = {5};
    int[] netzkonf6 = {};
    NeuronalesNetz err1 = new NeuronalesNetz();
    err1.initNetz(netzkonf5);
    NeuronalesNetz err2 = new NeuronalesNetz();
    err2.initNetz(netzkonf6);
    NeuronalesNetz nn4 = new NeuronalesNetz();
    nn4.initNetz(netzkonf1);
    nn4.initGewichte(gewichte3);
    nn4.computeNN(input1);*/
  }
}
