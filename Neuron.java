public class Neuron {
    Double[] weight;
    double bias; // muss noch initialisiert werden
    public double compute(Double[] input){
        double result = 0;
        for(int i = 0; i < input.length; i++) {
            result+= input[i] * weight[i];
        }
        result += bias;
        return sigmoid(result);
    }
    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
    private double tanh(double x) {
        return (Math.exp(x)-Math.exp(-x)/Math.exp(x)+Math.exp(-x));
    }
}
