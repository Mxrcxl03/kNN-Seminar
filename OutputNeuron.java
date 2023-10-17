public class OutputNeuron extends Neuron{
    private int OutputNID;
    public OutputNeuron(){
        super();
    }
    public OutputNeuron(int NID){
        super();
        this.OutputNID = NID;
    }
    @Override
    public double getValue() {
        return this.value;
    }

    @Override
    public int getNID() {
        return this.OutputNID;
    }
}
