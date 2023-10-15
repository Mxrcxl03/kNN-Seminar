public class Neuron {
    private static int nid;
    private double value;
    private double weight[];
    public Neuron(){
        this.nid = nid;
        nid++;
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

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setWeight(double[] weight) {
        this.weight = weight;
    }
}
