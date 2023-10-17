import java.util.Random;

public class NeuronalesNetz {
    int input;
    int hidden;
    int output;
    Neuron layer[][];
    double weights[][][]; //1. Tiefe - 1; 2. VON; 3. ZU;

    public void initNetz(int numIHO[]) { // Jeweilige Anzahlen an Neuronen
        this.input = numIHO[0];
        this.hidden = numIHO[1];
        this.output = numIHO[2];
        this.layer = new Neuron[3][];
        for(int i = 0; i < layer.length; i++){
            this.layer[i] = new Neuron[numIHO[i]];
        }
    }
    public void initGewichteRandom(){
        Random random = new Random();
        double[][] weightsIH = new double[input][hidden];
        double[][] weightsHO = new double[hidden][output];
        weights = new double[2][][];
        weights[0] = weightsIH;
        weights[1] = weightsHO;
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][j].length; k++) {
                    weights[i][j][k] = random.nextGaussian(-1,1);
                }
            }
        }
    }

}
