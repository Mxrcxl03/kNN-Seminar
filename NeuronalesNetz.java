/**
 * Erstellung, Modellierung und Berechnung eines neuronalen Netzes anhand von einer frei gewählten Anzahl
 * von Ebenen (Layer) sowie Neuronen pro Layer (Input, Hidden, Output). Beides als positive Ganzzahlen.
 * Außerdem können sowohl zufällige, als aber auch eingelese Gewichtswerte für das neuronale Netz festgelegt werden.
 *
 * @author Justin Emmerich, Marcel Koriath, Lars Niederweis
 * @since 16-10-2023
 * @version 1.0
 */

import java.util.Random;

public class NeuronalesNetz {
  // String zum Wechseln der einzelnen Aktivierungsfunktionen
  private static String activationFunction;
  // Non-rectangular Array d.h. Anzahl der Spalten im Array kann unterschiedlich sein
  // Neuron[x][y] -> x = Layer (z.B. 0 = Input), y = Neuron in dem entsprechenden Layer
  protected Neuron[][] layer;

  // double[Netztiefe - 1][von (untere Ebene)][zu (oberer Ebene)]
  protected double[][][] weights;

  private int weightSize;
  //Hilfe zur Gewicht Instanzierung
  //Gibt die Aktuelle "Tiefe" der Weights an.

  // Initialisierung der Netztopologie mit selbst gewählten positiven Ganzzahlen
  public void initNetz(int[] netzkonf) {
    if(netzkonf.length < 2) {
      throw new RuntimeException("Netzkonfiguration ungültig, überprüfen Sie ihre Netztopologie (mind. 2 positive Ganzzahlen > 0)!");
    }

    // Festlegen der Anzahl der Zeilen (Layer-Anzahl) anhand der Anzahl von Elementen in der Netzkonfig
    this.layer = new Neuron[netzkonf.length][];

    // Festlegen der unterschiedlichen Zeilenlängen (d.h. Spaltenanzahl) anhand den Werten aus der Netzkonfig
    for(int i = 0; i < netzkonf.length; i++) {
      this.layer[i] = new Neuron[netzkonf[i]];
    }

    // Nach der Initialisierung (NULL-Zuweisung), Erstellen neuer Objekte
    for(int i = 0; i < getInputNeuronCount(); i++) {
      // Erstellen eines neuen InputNeuron-Objekts mit Übergabe von i als jeweilige id
      this.layer[0][i] = new InputNeuron(i);
    }

    for(int i = 0; i < getOutputNeuronCount(); i++) {
      this.layer[netzkonf.length - 1][i] = new OutputNeuron(i);
    }

    // Sollte die Anzahl der Elemente in Netzkonfig = 3 sein -> 3-Layer (Input[0] - Hidden[1] - Output[2])
    if(netzkonf.length == 3) {
      for(int i = 0; i < getHiddenNeuronCount(); i++) {
        this.layer[1][i] = new HiddenNeuron(i);
      }
    } else {
      // Für alle neuronalen Netze mit 4 oder mehr Ebenen (d.h. 2+ Hidden-Layer)
      for(int index = 1; index < getLayerCount() - 1; index++) {
        for(int i = 0; i < layer[index].length; i++) {
          this.layer[index][i] = new HiddenNeuron(i);
        }
      }
    }
    //System.out.println("Netzkonfiguration erfolgreich!");
  }

  // Initialisierung von Gewichten im neuronalen Netz mit zufällig gewählten Zahlen zwischen [-1;1]
  public void initGewichteRandom() {
    this.weights = new double[getLayerCount() - 1][][];
    Random r = new Random();
    for(int i = 0; i < weights.length; i++) {
      // Initialisierung von Gewichten zwischen allen Neuronen eines Layer[i] (Start = 0)
      // mit allen Neuronen des nächsten Layers[i+1] -> vollvermaschtes, neuronales Netz
      this.weights[i] = new double[this.layer[i].length][this.layer[i+1].length];
      // j = (von) Neuron auf Layer[i], k = (zu) Neuron auf Layer[i+1]
      for(int j = 0; j < weights[i].length; j++) {
        for(int k = 0; k < weights[i][j].length; k++) {
          // Nach Gauß normal-verteilter Zufallswert zwischen [-1;1], um Streuung von Messwerten (Gewichte) zu erhalten
          // Abweichungen der Messwerte durch Normalverteilung in guter Näherung beschrieben
          this.weights[i][j][k] = r.nextGaussian(-1, 1);
          //System.out.println(this.weights[i][j][k]);
        }
      }
    }
    //System.out.println("Zufällige Gewichtswerte dem neuronalen Netz erfolgreich zugewiesen!");
  }

  // Testmethode zum Einlesen von Gewichten, um computeNN zu testen - NN mit 2-Layer selber berechnen
  public void initGewichteTest(double[][] gewichte) {
    this.weights = new double[1][gewichte.length][gewichte[0].length];
    for(int i = 0; i < gewichte.length; i++) {
      for(int j = 0; j < gewichte[0].length; j++) {
        this.weights[0][i][j] = gewichte[i][j];
        //System.out.print(this.weights[0][i][j] + " ");
      }
      //System.out.println();
    }
  }

  // Initialisierung von Gewichten im neuronalen Netz mit eingelesen Werten
  public void initGewichte(double[][][] gewichte) {
    // Überprüfen, ob die Anzahl der eingelesenen Gewichte den erwarteten Verbindungen im Netzwerk entspricht
    this.weights = new double[getLayerCount() - 1][][];
    int expectedConnections = 0;
    for (int i = 0; i < getLayerCount() - 1; i++) {
      expectedConnections += (layer[i].length) * layer[i + 1].length;
    }

    int actualConnections = 0;
    for (int i = 0; i < gewichte.length; i++) {
        actualConnections += gewichte[i][0].length * gewichte[i].length;
    }

    if (actualConnections != expectedConnections) {
      throw new RuntimeException("Die Anzahl der eingelesenen Gewichte entspricht nicht der erwarteten Anzahl an Verbindungen.");
    }
    if(layer.length != gewichte.length+1){
      throw new RuntimeException("Die Anzahl der eingelesenen Gewichte entspricht nicht der erwarteten Anzahl an Verbindungen.");
    }
    this.weights = gewichte;
    //TODO Check, ob die Anzahl eingelesene Gewichtswerte alle Verbindungen (IN * L1H + L1H * L2H...) abdecken kann - RuntimeException
  }

  // Berechne das neuronale Netz mithilfe von festgelegten Werten im Input (Input-Vektor)
  public void computeNN(double[] input) {
    // Überprüfen, ob jedem InputNeuron ein Wert aus input zugewiesen werden kann
    if(input.length != getInputNeuronCount()) {
      throw new RuntimeException(
        "Die Elemente im Input-Vektor entsprechen nicht der Anzahl der Neuronen im Input-Layer. " +
        "Bitte überprüfen Sie ihre Eingabe und stellen Sie sicher, dass jedem Input Neuron ein Wert zugewiesen wird!");
    }

    // Zuweisung des Startwerts (aus input) zu dem jeweiligen Input-Neuron
    for(int i = 0; i < input.length; i++) {
      // Neuron[0][i] ist immer ein InputNeuron für jedes i, da Input-Layer = 0.
      // Daher Casting auf InputNeuron ohne Bedenken möglich, um daraufhin Methode setValue() aufzurufen
      ((InputNeuron) this.layer[0][i]).setValue(input[i]);
      //System.out.println(this.layer[0][i].getNID() + " - " + this.layer[0][i].getValue());
    }

    // Vereinfachte Berechnung sollte es nur Input und Output-Layer geben (d.h. Layer = 2)
    if(getLayerCount() == 2) {
      double sum = 0;
      for (int i = 0; i < getOutputNeuronCount(); i++) {
        for (int j = 0; j < getInputNeuronCount(); j++) {
          sum += this.layer[0][j].getValue() * this.weights[0][j][i];
        }
        this.layer[1][i].value = sum;
        System.out.println(this.layer[1][i].value);
      }
    } else {
      // Berechnung der Hidden / Output Neuron Werte für eine beliebige Anzahl an Hidden-Layern (2 oder mehr)
      for(int ebene = 1; ebene < getLayerCount(); ebene++) {
        for(int i = 0; i < layer[ebene].length; i++) {
          double sum = 0;
          for(int j = 0; j < layer[ebene-1].length; j++) {
            sum += this.layer[ebene-1][j].getValue() * this.weights[ebene-1][j][i];
          }
          this.layer[ebene][i].value = computeActivationFunction(sum);
          System.out.println(this.layer[ebene][i].getValue());
        }
      }
    }
  }

  // Je nach festgelegter Aktivierungsfunktion (als String) ausführen unterschiedlicher Aktivierungsfunktionen
  public double computeActivationFunction(double value) {
    switch (activationFunction) {
      case "id":
      case "identity":
        return identity(value);
      case "sig":
      case "sigmoid":
        return sigmoid(value);
      case "tan":
      case "tanh":
        return tanh(value);
      default:
        throw new IllegalArgumentException("Ungültiger Name für die Aktivierungsfunktion!");
    }
  }

  // Aktivierungsfunktionen
  public double identity(double x) {
    return x;
  }

  public double sigmoid(double x) {
    return 1 / (1 + Math.exp(-x));
  }

  public double tanh(double x) {
    return (Math.exp(x)-Math.exp(-x)/Math.exp(x)+Math.exp(-x));
  }

  // Erhalte aktuell, verwendete Aktivierungsfunktion
  public static String getActivationFunction() {
    System.out.println("Möglichkeiten: identity (id), sigmoid (sig) und tanh (tan).");
    return activationFunction;
  }

  // Wechseln der Aktivierungsfunktion zur Berechnung der Werte im NN
  public static void setActivationFunction(String function) {
    activationFunction = function;
    System.out.println("Neue Aktivierungsfunktion: " + function);
  }

  // Getter für die Anzahl der Layers sowie die Neuronen pro Layer
  // Anzahl der InputNeuronen durch Auslesen der Zeilenlänge der ersten Ebene (= Input)
  public int getInputNeuronCount() {
    return this.layer[0].length;
  }

  // Anzahl der HiddenNeuronen durch Auslesen aller Layer zwischen Input-Output und Summieren der jeweiligen Zeilenlängen
  public int getHiddenNeuronCount() {
    int hidden = 0;
    for(int i = 1; i < layer.length - 1; i++) {
      hidden += this.layer[i].length;
    }
    return hidden;
  }

  // Anzahl der OutputNeuronen durch Auslesen der Zeilenlänge der letzten Ebene (= Output)
  public int getOutputNeuronCount() {
    return this.layer[layer.length - 1].length;
  }

  // Anzahl der Layer im neuronalen Netz
  public int getLayerCount() {
    return this.layer.length;
  }

  // Anzahl der Hidden Layer im neuronalen Netz (-2 für Input / Output Layer)
  public int getHiddenLayerCount() {
    return this.layer.length - 2;
  }

}
