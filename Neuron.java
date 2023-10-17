public abstract class Neuron extends NeuronalesNetz{
    protected double value;
    private double weight[];
    public Neuron(){
        this.value = 0;
    }
    public double compute(double[] input){
        double result = 0;
        for(int i = 0; i < input.length; i++) {
            result+= input[i] * weight[i];
        }
        return sigmoid(result);
    }
    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
    private double tanh(double x) {
        return (Math.exp(x)-Math.exp(-x)/Math.exp(x)+Math.exp(-x));
    }

    public abstract double getValue();
    public abstract int getNID();
}
