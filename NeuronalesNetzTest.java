import java.util.concurrent.ThreadLocalRandom;
public class NeuronalesNetzTest {
  public static void main(String[] args) {
    String id = "id";
    String logi = "logi";
    String tanh = "tanh";

    int[] netzkonf1 = {3, 3, 4};
    double[] input1 = {1, 0, 0};

    double[][][] gewichtsdatei1 = {
      { {-0.081, 0.08, -0.04}, {0.06, 0.02, -0.003}, {-0.01, 0.003, -0.09}, {0.08, -0.09, -0.05} },
      { {-0.008, 0.01, 0.01, 2.9E-4}, {0.06, -0.06, -0.027, -0.01}, {0.04, 0.06, 0.08, 0.08}, {-0.08, 0.06, 0.09, -0.001} }};

    NeuronalesNetz n1 = new NeuronalesNetz();
    n1.initNetz(netzkonf1);
    n1.initGewichte(gewichtsdatei1);

    for(int i = 0; i < netzkonf1[1]; i++) {
      n1.layer[1][i].setActivationFunction(logi);
    }

    n1.computeNN(input1);
    //n1.ausgabeOutput();

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

    double[][] inputMenge = new double[][]{inputT1, inputT2, inputT3, inputT4, inputT5};
    double[][] idealMenge = new double[][]{idealOutT1, idealOutT2, idealOutT3, idealOutT4, idealOutT5};

      // Feste Lernrate
    double lernrate = 0.01;
    // Iterationanzahl
    int iterationen = 10000;
    // Error-Array mit der Entwicklung des Fehlers auf die Werte
    double[] errorIteration = new double[iterationen];

    // Nur ein Trainingsdatensatz für Vergleich der Entwicklung des Fehlers sonst 5 unterschiedliche Werte
    // Ändern durch Austauschen der Parameter T1 -> TX
    for(int i = 0; i < iterationen; i++) {
      int randomNumber = i; //ThreadLocalRandom.current().nextInt(0,5)
      randomNumber %= 5;
     // n1.backpropagation(inputMenge[randomNumber], idealMenge[randomNumber], lernrate);  //zufällige wahl der 5 gewählten inputs
      n1.backpropagation(inputT1, idealOutT1, lernrate);
      //n1.ausgabeOutput();
      errorIteration[i] = n1.computeError(idealOutT1);
    }

    /*for(int i = 0; i < iterationen; i++) {
      // Um durch alle 5 Trainingsdaten durchzugehen
      int cycleThroughTraindata = (i % 5) + 1;
      if (cycleThroughTraindata == 1) {
        n1.backpropagation(inputT1, idealOutT1, lernrate);
        //n1.ausgabeOutput();
        errorIteration[i] = n1.computeError(idealOutT1);
      }
      if (cycleThroughTraindata == 2) {
        n1.backpropagation(inputT2, idealOutT2, lernrate);
        //n1.ausgabeOutput();
        errorIteration[i] = n1.computeError(idealOutT2);
      }
      if (cycleThroughTraindata == 3) {
        n1.backpropagation(inputT3, idealOutT3, lernrate);
        //n1.ausgabeOutput();
        errorIteration[i] = n1.computeError(idealOutT3);
      }
      if (cycleThroughTraindata == 4) {
        n1.backpropagation(inputT4, idealOutT4, lernrate);
        //n1.ausgabeOutput();
        errorIteration[i] = n1.computeError(idealOutT4);
      }
      if (cycleThroughTraindata == 5) {
        n1.backpropagation(inputT5, idealOutT5, lernrate);
        //n1.ausgabeOutput();
        errorIteration[i] = n1.computeError(idealOutT5);
      }
    }*/

    // Ausgabe der einzelnen Fehlerwerte untereinander
    for(int i = 0; i < errorIteration.length; i++) {
      System.out.println(errorIteration[i]);
    }

    /*int[] netzkonf2 = {2, 2, 2};
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
    //n2.ausgabeOutput();*/
  }
}
