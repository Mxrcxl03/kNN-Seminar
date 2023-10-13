import java.util.Random;

public class NeuronalesNetz {
    int input;
    int hidden;
    int output;
    Neuron layer[][];
    private double weights[][][]; //1. Teife - 1; 2. VON; 3. ZU;
    double weightsIH[][];
    double weightsHO[][];

    public void init(int numIHO[]) { // Jeweilige Anzahlen an Neuronen
        this.input = numIHO[0];
        this.hidden = numIHO[1];
        this.output = numIHO[2];
    }
    public void initWeights(){
        Random random = new Random();
        this.weightsIH = new double[input][hidden];
        this.weightsHO = new double[hidden][output];
        double g = random.nextGaussian();
    }
    public double[][][] getWeights() {
        return weights;
    }
}
