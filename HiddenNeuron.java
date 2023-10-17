public class HiddenNeuron extends Neuron{
    private  int hiddenNID;
    public HiddenNeuron(){
        super();
    }
    public HiddenNeuron(int NID){
        super();
        this.hiddenNID = NID;
    }
    @Override
    public double getValue() {
        return this.value;
    }

    @Override
    public int getNID() {
        return this.hiddenNID;
    }
}
