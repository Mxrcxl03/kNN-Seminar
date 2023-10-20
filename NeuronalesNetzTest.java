public class NeuronalesNetzTest {
  public static void main(String[] args) {
    String func1 = "id";
    String func2 = "sigmoid";
    String func3 = "tanh";

    Neuron neuron1;
    Neuron neuron2;
    Neuron neuron3;
    Neuron neuron4;

    int[] netzkonf1 = {5, 8, 2};
    int[] netzkonf2 = {5, 8, 4, 2};
    int[] netzkonf3 = {4, 1};
    int[] netzkonf4 = {4, 2};

    double[] input1 = {2.0, 1.0, 1.1, -3, 5};
    double[] input2 = {5.0, 2.0, 3.0, 2.0}; // Für netzkonf3 und netzkonf4

    double[][] gewichte1 = {{0.5}, {-0.2}, {0.1}, {0.2}}; // Für netzkonf3 [4][1]
    double[][] gewichte2 = {{0.5, 0.2}, {-0.3, 0.1}, {0.1, 0.7}, {-0.6, 0.3}}; // Für netzkonf4 [4][2]

    NeuronalesNetz.setActivationFunction(func1); // Setzen der Aktivierungsfunktion "Identity"

    NeuronalesNetz nn1 = new NeuronalesNetz();
    nn1.initNetz(netzkonf1);
    nn1.initGewichteRandom();
    nn1.computeNN(input1);

    System.out.println(nn1.getLayerCount()); // 3
    //System.out.println(nn1.layer[0][1].getValue());
    //System.out.println(nn1.layer[1][6].getValue());
    //System.out.println(nn1.layer[2][1].getValue());

    // Ausgabe der einzelnen NIDs testen
    neuron1 = nn1.layer[0][1];
    neuron2 = nn1.layer[1][1];
    neuron3 = nn1.layer[2][1];
    System.out.println(neuron1.getNID()); // Input Neutron 1
    System.out.println(neuron2.getNID()); // Hidden Neutron 1
    System.out.println(neuron3.getNID()); // Output Neutron 1



    /*nn1.initGewichteRandom();
    for (int i = 0; i < 2; i++) {
      System.out.println("Von Layer " + i + " zu " + (i+1));
      for (int j = 0; j < netzkonf1[i]; j++) {
        for (int k = 0; k < netzkonf1[i+1]; k++) {
          System.out.println(nn1.weights[i][j][k]);
        }
      }
      System.out.println("");
    }*/

    NeuronalesNetz nn2 = new NeuronalesNetz();
    nn2.initNetz(netzkonf2);
    /*neuron1 = nn2.layer[0][2];
    neuron2 = nn2.layer[1][6];
    neuron3 = nn2.layer[2][3];
    neuron4 = nn2.layer[3][1];
    System.out.println(neuron1.getNID()); // 2
    System.out.println(neuron2.getNID()); // 6
    System.out.println(neuron3.getNID()); // 3
    System.out.println(neuron4.getNID()); // 1*/

    NeuronalesNetz nn3 = new NeuronalesNetz();
    nn3.initNetz(netzkonf3);
    nn3.initGewichte(gewichte1);
    nn3.computeNN(input2);

    NeuronalesNetz nn4 = new NeuronalesNetz();
    nn4.initNetz(netzkonf4);
    nn4.initGewichte(gewichte2);
    nn4.computeNN(input2);

    /* Fehler absichtlich verursacht
    int[] netzkonf5 = {5};
    int[] netzkonf6 = {};
    NeuronalesNetz err1 = new NeuronalesNetz();
    err1.initNetz(netzkonf5);
    NeuronalesNetz err2 = new NeuronalesNetz();
    err2.initNetz(netzkonf6);*/
  }
}
