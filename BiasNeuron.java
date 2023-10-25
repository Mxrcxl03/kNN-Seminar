public class BiasNeuron extends Neuron {
    // Wert eines Bias-Neuron = 1
    private static final double biasValue = 1;

    // biasNID gibt Layer an, auf dem das Bias-Neuron liegt
    private int biasNID;

    public BiasNeuron() {
        super();
        this.value = biasValue;
        this.biasNID = 0;
    }

    public static double getBiasValue() {
        return biasValue;
    }

    @Override
    public int getNID() {
        return this.biasNID;
    }
}
