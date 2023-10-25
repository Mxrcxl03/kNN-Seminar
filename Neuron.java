public abstract class Neuron extends NeuronalesNetz {
  // Wert eines einzelnen Neurons
  protected double value;

  // Aktivierungsfunktion des Neurons
  protected String activationFunction;

  // Standard-Konstruktor zum Initialisieren eines neuen Neurons mit dem Wert 0 und Aktivierungsfunktion "id"
  public Neuron() {
    this.value = 0;
    this.activationFunction = "id";
  }

  // getNID() zum Auslesen der ID innerhalb eines Layers
  public abstract int getNID();

  public double getValue() {
    return this.value;
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
