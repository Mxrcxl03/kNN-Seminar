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
        this.output = numIHO[numIHO.length-1];
        this.layer = new Neuron[numIHO.length][];
        for(int i = 0; i < layer.length; i++){
            this.layer[i] = new Neuron[numIHO[i]];
        }

        for(int i = 0; i < layer[0].length; i++){
            this.layer[0][i] = new InputNeuron(i);
        }
        for(int k = 1; k < layer.length-1; k++) {
            for (int i = 0; i < layer[k].length; i++) {
                this.layer[k][i] = new HiddenNeuron(i);
            }
        }
        for(int i = 0; i < layer[layer.length-1].length; i++){
            this.layer[layer.length-1][i] = new OutputNeuron(i);
        }
        System.out.println("1");
    }
    public void initGewichteRandom(){
        Random random = new Random();
        double[][] weightsIH = new double[input][hidden];
        double[][] weightsHO = new double[hidden][output];
        weights = new double[layer.length-1][][];
        weights[0] = weightsIH;
        weights[1] = weightsHO;
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                for (int k = 0; k < weights[i][j].length; k++) {
                    weights[i][j][k] = random.nextGaussian(-1,1);
                }
            }
        }
        System.out.println("1");
    }
    public void computeNN(double[] inputValue){
        for(int i = 0; i < inputValue.length; i++){
            ((InputNeuron) this.layer[0][i]).setValue(inputValue[i]);
        }

        for(int i = 0; i < layer[1].length; i++){
            double sum = 0;
            for(int k = 0; k < layer[0].length; k++){
                sum += this.layer[0][k].getValue() * weights[0][k][i];
            }
            this.layer[1][i].value = sigmoid(sum);
        }
        for(int i = 0; i < layer[2].length; i++){
            double sum = 0;
            for(int k = 0; k < layer[1].length; k++){
                sum += this.layer[1][k].getValue() * weights[1][k][i];
            }
            this.layer[2][i].value = sigmoid(sum);
            System.out.println(this.layer[2][i].value);
        }
    }
    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }
    private double tanh(double x) {
        return (Math.exp(x)-Math.exp(-x)/Math.exp(x)+Math.exp(-x));
    }

}
