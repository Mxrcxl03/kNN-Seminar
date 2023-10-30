public abstract class Neuron extends NeuronalesNetz {
  // Wert eines einzelnen Neurons vor Aktivierungsfunktion (Netzinput, gewichtete Summe)
  protected double net;
  // Wert eines einzelnen Neurons nach Aktivierungsfunktion
  protected double value;
  // Delta-Wert eines Neurons zum Lernen mit Backpropagation
  protected double delta;

  // Aktivierungsfunktion des Neurons
  protected String activationFunction;

  // Standard-Konstruktor zum Initialisieren eines neuen Neurons mit dem Wert 0 und Aktivierungsfunktion "id"
  public Neuron() {
    this.net = 0;
    this.value = 0;
    this.activationFunction = "id";
  }

  // getNID() zum Auslesen der ID innerhalb eines Layers
  public abstract int getNID();

  public double getValue() {
    return this.value;
  }

  public double getNet() {
    return this.net;
  }

  public double getDelta() {
    return this.delta;
  }

  public String getActivationFunction() {
    return this.activationFunction;
  }

  // Möglichkeiten: id, logi und tanh
  public void setActivationFunction(String function) {
    this.activationFunction = function;
    //System.out.println("Aktivierungsfunktion auf: " + this.activationFunction + " geändert.");
  }
}
