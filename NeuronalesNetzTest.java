public class NeuronalesNetzTest {
    public static void main(String[] args) {
        int[] arr = {4 , 4 , 2};
        NeuronalesNetz NN = new NeuronalesNetz();
        NN.initNetz(arr);
        NN.initGewichteRandom();
        double[] input = {1.3, 3.4, 10.2, -4.9};
        NN.computeNN(input);
    }
}
