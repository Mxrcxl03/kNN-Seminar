/**
 * Erstellung, Modellierung und Berechnung eines neuronalen Netzes anhand von einer frei gewählten Anzahl
 * von Ebenen (Layer) sowie Neuronen pro Layer (Input, Hidden, Output). Beides als positive Ganzzahlen.
 * Außerdem können sowohl zufällige, als aber auch eingelese Gewichtswerte für das neuronale Netz festgelegt werden.
 *
 * @author Justin Emmerich, Marcel Koriath, Lars Niederweis
 * @since 16-10-2023
 * @version 1.2
 */

import java.util.Random;

public class NeuronalesNetz {
  // Non-rectangular Array d.h. Anzahl der Spalten im Array kann unterschiedlich sein
  // Neuron[x][y] -> x = Layer (z.B. 0 = Input), y = Neuron in dem entsprechenden Layer
  protected Neuron[][] layer;

  // double[Netztiefe - 1][von (untere Ebene)][zu (oberer Ebene)]
  protected double[][][] weights;

  // Initialisierung der Netztopologie mit selbst gewählten positiven Ganzzahlen
  public void initNetz(int[] netzkonf) {
    if(netzkonf.length < 2) {
      throw new RuntimeException("Netzkonfiguration ungültig, überprüfen Sie ihre Netztopologie (mind. 2 positive Ganzzahlen > 0)!");
    }

    // Festlegen der Anzahl der Zeilen (Layer-Anzahl) anhand der Anzahl von Elementen in der Netzkonfig
    this.layer = new Neuron[netzkonf.length][];

    // Festlegen der unterschiedlichen Zeilenlängen (d.h. Spaltenanzahl) anhand den Werten aus der Netzkonfig
    for(int i = 0; i < netzkonf.length; i++) {
      if (i == netzkonf.length - 1) {
        this.layer[i] = new Neuron[netzkonf[i]];
        break;
      }
      // Bias +1 für jeden Layer außer Output-Layer
      this.layer[i] = new Neuron[netzkonf[i] + 1];
    }

    // Nach der Initialisierung (NULL-Zuweisung), Erstellen neuer Objekte
    for(int i = 0; i < netzkonf[0]; i++) {
      // Erstellen eines neuen InputNeuron-Objekts mit Übergabe von i + 1 als jeweilige id (Anfangswert = 1)
      this.layer[0][i] = new InputNeuron(i + 1);
    }
    this.layer[0][netzkonf[0]] = new BiasNeuron();


    // Erstellung Output-Neuronen ohne Bias-Neuron
    for(int i = 0; i < netzkonf[netzkonf.length - 1]; i++) {
      // Erstellen eines neuen OutputNeuron-Objekts mit Übergabe von i + 1 als jeweilige id (Anfangswert = 1)
      this.layer[netzkonf.length - 1][i] = new OutputNeuron(i + 1);
    }

    // Sollte die Anzahl der Elemente in Netzkonfig = 3 sein -> 3-Layer (Input[0] - Hidden[1] - Output[2])
    if(netzkonf.length == 3) {
      for(int i = 0; i < this.layer[1].length - 1; i++) {
        this.layer[1][i] = new HiddenNeuron(i + 1);
      }
      this.layer[1][netzkonf[1]] = new BiasNeuron();
    } else {
      // Für alle neuronalen Netze mit 4 oder mehr Ebenen (d.h. 2+ Hidden-Layer)
      for(int index = 1; index < getLayerCount() - 1; index++) {
        for(int i = 0; i < netzkonf[index]; i++) {
          this.layer[index][i] = new HiddenNeuron(i + 1);
        }
        this.layer[index][netzkonf[index]] = new BiasNeuron();
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
      this.weights[i] = new double[this.layer[i].length][this.layer[i+1].length - 1]; // !!! -1] !!! Warum?
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

  // Initialisierung von Gewichten im neuronalen Netz mit eingelesen Werten
  public void initGewichte(double[][][] gewichte) {
    // Überprüfen, ob die Anzahl der eingelesenen Gewichte den erwarteten Verbindungen im Netzwerk entspricht
    this.weights = new double[getLayerCount() - 1][][];

    int expectedConnections = 0;
    for (int i = 0; i < getLayerCount() - 1; i++) {
      if (i == getLayerCount() - 2) {
        expectedConnections += this.layer[i].length * this.layer[i + 1].length;
        break;
      }
      expectedConnections += this.layer[i].length * (this.layer[i + 1].length - 1);
    }

    int actualConnections = 0;
    for (int i = 0; i < gewichte.length; i++) {
        actualConnections += gewichte[i][0].length * gewichte[i].length;
    }

    if (actualConnections != expectedConnections) {
      throw new RuntimeException("Die Anzahl der eingelesenen Gewichte entspricht nicht der erwarteten Anzahl an Verbindungen.");
    }

    if(weights.length != gewichte.length) {
      throw new RuntimeException("Die Anzahl der eingelesenen Gewichte entspricht nicht der erwarteten Anzahl an Verbindungen.");
    }
    this.weights = gewichte;
  }

  // Berechne das neuronale Netz mithilfe von festgelegten Werten im Input (Input-Vektor)
  public void computeNN(double[] input) {
    // Überprüfen, ob jedem InputNeuron ein Wert aus input zugewiesen werden kann
    if(input.length != this.layer[0].length - 1) {
      throw new RuntimeException(
        "Die Elemente im Input-Vektor entsprechen nicht der Anzahl der Neuronen im Input-Layer. " +
        "Bitte überprüfen Sie ihre Eingabe und stellen Sie sicher, dass jedem Input Neuron ein Wert zugewiesen wird!");
    }

    // Zuweisung des Startwerts (aus input) zu dem jeweiligen Input-Neuron
    for(int i = 0; i < input.length; i++) {
      // Neuron[0][i] ist immer ein InputNeuron für jedes i, da Input-Layer = 0.
      // Daher Casting auf InputNeuron ohne Bedenken möglich, um daraufhin Methode setValue() aufzurufen
      ((InputNeuron) this.layer[0][i]).setValue(input[i]);
    }

    // Vereinfachte Berechnung sollte es nur Input und Output-Layer geben (d.h. Layer = 2)
    if(getLayerCount() == 2) {
      double sum = 0;
      // i = Neuron auf der oberen Schicht, j = Neuron auf der aktuellen Schicht
      for (int i = 0; i < this.layer[1].length; i++) {
        for (int j = 0; j < this.layer[0].length; j++) {
          sum += this.layer[0][j].getValue() * this.weights[0][j][i];
        }
        this.layer[1][i].value = computeActivationFunction(sum, this.layer[1][i]);
        //System.out.println(this.layer[1][i].value);
      }
    } else {
      // Berechnung der Hidden / Output Neuron Werte für eine beliebige Anzahl an Hidden-Layern (2 oder mehr)
      for(int ebene = 1; ebene < getLayerCount(); ebene++) {
        int outputNeuronen = layer[ebene].length - 1;
        if (ebene == getLayerCount() - 1) {
          outputNeuronen = layer[ebene].length;
        }
        for(int i = 0; i < outputNeuronen; i++) {
          double sum = 0;
          for(int j = 0; j < layer[ebene-1].length; j++) {
            sum += this.layer[ebene-1][j].getValue() * this.weights[ebene-1][j][i];
          }
          this.layer[ebene][i].value = computeActivationFunction(sum, this.layer[ebene][i]);
          //System.out.println(this.layer[ebene][i].getValue());
        }
      }
    }
    ausgabeOutput();
  }

  // Ausgabe der berechneten Werte für alle Output-Neuronen
  public void ausgabeOutput() {
    for(int i = 0; i < this.layer[getLayerCount() - 1].length; i++) {
      System.out.println(this.layer[getLayerCount() - 1][i].getValue());
    }
  }

  // Je nach festgelegter Aktivierungsfunktion (als String) ausführen unterschiedlicher Aktivierungsfunktionen
  public double computeActivationFunction(double value, Neuron output) {
    switch (output.getActivationFunction()) {
      case "id":
        return identity(value);
      case "logi":
        return logi(value);
      case "tanh":
        return tanh(value);
      default:
        throw new IllegalArgumentException("Ungültiger Name für die Aktivierungsfunktion!");
    }
  }

  // Aktivierungsfunktionen
  public double identity(double value) {
    return value;
  }

  public double logi(double value) {
    return 1 / (1 + Math.exp(-value));
  }

  public double tanh(double value) {
    return ((Math.exp(value)-Math.exp(-value))/(Math.exp(value)+Math.exp(-value)));
  }

  // Anzahl der Layer im neuronalen Netz
  public int getLayerCount() {
    return this.layer.length;
  }
}
