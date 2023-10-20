public class HiddenNeuron extends Neuron {
  // ID für jedes HiddenNeuron zum vereinfachten Auslesen eines bestimmten HiddenNeurons in seinem Layer
  private int hiddenNID;

  // Konstruktor zur Erstellung eines HiddenNeuron ohne ID
  public HiddenNeuron() {
    super();
  }

  // Konstruktor mit der Angabe einer ID für das HiddenNeuron
  public HiddenNeuron(int nid) {
    super();
    this.hiddenNID = nid;
  }

  @Override
  public double getValue() {
    // Möglichkeit: Berechnung des Wertes mit Aktivierungsfunktion innerhalb dieser Funktion zu berechnen
    return this.value;
  }

  @Override
  public int getNID() {
    return this.hiddenNID;
  }
}
