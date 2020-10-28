package Avatar.Elemental.fire.AI.classic.test;

import Avatar.Elemental.fire.AI.classic.RandomWeightSeeder;
import Avatar.Elemental.fire.AI.classic.NeuralNetwork;
import Avatar.Elemental.fire.AI.classic.NeuralNetworkSpecification;
import Avatar.Elemental.water.signals.VectorSignal;
import Avatar.Elemental.wind.BookOfRass;
import org.junit.Test;

public class NeuralNetworkTest
{
    @Test
    public void test()
    {
        NeuralNetworkSpecification spec = new NeuralNetworkSpecification();
        int[] hiddenLayers = new int[10];
        for(int i = 0; i < hiddenLayers.length; i++)
        {
            hiddenLayers[i] = 1;
        }

        spec.setInputDimension(1);
        spec.setHiddenLayerDimensions(hiddenLayers);
        spec.setOutputDimension(1);

        spec.setActivationFunction(BookOfRass.GELU);
        double[] input = {10};
        NeuralNetwork neuralNetwork = new NeuralNetwork(spec, new RandomWeightSeeder());
        System.out.println("Prediction: "+neuralNetwork.predict(new VectorSignal(input)));
    }
}