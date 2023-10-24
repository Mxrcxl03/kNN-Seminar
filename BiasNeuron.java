public class BiasNeuron extends Neuron{
    private int biasNID;

    public BiasNeuron() { super(); }

    public BiasNeuron(int nid){
        super();
        this.biasNID = nid;
    }

    @Override
    public double getValue() {
        return this.value;
    }

    @Override
    public int getNID() {
        return this.biasNID;
    }
}
