public class InputNeuron extends Neuron {
	// ID für jedes InputNeuron zum vereinfachten Auslesen eines bestimmten InputNeurons in seinem Layer
	private int inputNID;

	// Konstruktor zur Erstellung eines InputNeuron ohne ID
	public InputNeuron() {
		super();
	}

	// Konstruktor mit der Angabe einer ID für das Input Neuron
	public InputNeuron(int nid) {
		super();
		this.inputNID = nid;
	}

	// Setzen eines Wert für ein InputNeuron
	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public int getNID() {
		return this.inputNID;
	}
}
