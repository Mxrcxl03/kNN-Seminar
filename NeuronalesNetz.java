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
        weights = new double[2][][];
        weights[0] = weightsIH;
        weights[1] = weightsHO;

        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][j].length; k++) {
                    weights[i][j][k] = random.nextDouble() * 2 - 1;
                }
            }
        }
    }
    public double[][][] getWeights() {
        return weights;
    }
}
