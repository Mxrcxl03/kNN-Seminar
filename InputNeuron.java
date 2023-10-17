public class InputNeuron extends Neuron{
    private int inputNID;
    public InputNeuron(){
        super();
    }
    public InputNeuron(int NID){
        super();
        this.inputNID = NID;
    }
    @Override
    public double getValue() {
        return this.value;
    }

    @Override
    public int getNID() {
        return this.inputNID;
    }
    public void setValue(double value){
        this.value = value;
    }
}
