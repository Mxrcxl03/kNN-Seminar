public class OutputNeuron extends Neuron {
  // ID für jedes OutputNeuron zum vereinfachten Auslesen eines bestimmten OutputNeurons in seinem Layer
  private int outputNID;

  // Konstruktor zur Erstellung eines OutputNeuron ohne ID
  public OutputNeuron() {
    super();
  }

  // Konstruktor mit der Angabe einer ID für das OutputNeuron
  public OutputNeuron(int nid) {
    super();
    this.outputNID = nid;
  }

  @Override
  public int getNID() {
    return this.outputNID;
  }
}
