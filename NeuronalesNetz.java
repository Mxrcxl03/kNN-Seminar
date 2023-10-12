import java.util.Random;

public class NeuronalesNetz {
    int input;
    int hidden;
    int output;
    public double weightsIH[][];
    public double weightsHO[][];
    public void init(int input, int hidden, int output) { // Jeweilige Anzahlen an Neuronen
        this.input = input;
        this.hidden = hidden;
        this.output = output;
    }
    public void initWeights(){
        Random random = new Random();
        this.weightsIH = new double[input][hidden];
        this.weightsHO = new double[hidden][output];
    }
}
