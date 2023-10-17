public abstract class Neuron extends NeuronalesNetz{
    protected double value;
    public Neuron(){
        this.value = 0;
    }
    public abstract double getValue();
    public abstract int getNID();
}
