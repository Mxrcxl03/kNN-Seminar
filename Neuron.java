public abstract class Neuron extends NeuronalesNetz {
  // Wert eines einzelnen Neurons
  protected double value;

  // Standard-Konstruktor zum Initialisieren eines neuen Neurons mit dem Wert 0
  public Neuron() {
    this.value = 0;
  }

  // getValue() für alle abgeleiteten Klassen (d.h. alle Arten von Neuronen) gelten
  public abstract double getValue();

  // getNID() zum Auslesen der ID innerhalb eines Layers
  public abstract int getNID();
}
