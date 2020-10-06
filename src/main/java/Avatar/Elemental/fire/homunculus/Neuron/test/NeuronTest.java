package Avatar.Elemental.fire.homunculus.Neuron.test;

import Avatar.Elemental.fire.homunculus.Neuron.ActivationFunction;
import Avatar.Elemental.fire.homunculus.Neuron.HiddenLayerSlice;
import Avatar.Elemental.fire.homunculus.Neuron.Neuron;
import Avatar.Elemental.water.VectorSignal;
import org.junit.Test;

public class NeuronTest
{
    @Test
    public void test()
    {
        int inputDimension = 2;
        int outputDimension = 1;

        int[] hiddenLayers = new int[4];
        for(int i = 0; i < hiddenLayers.length; i++)
        {
            hiddenLayers[i] = 2;
        }

        double[] inputData1 = {0.1,1};
        double[] outputData1 = {0.5};
        VectorSignal input1 = new VectorSignal(inputData1);
        VectorSignal output1 = new VectorSignal(outputData1);

        Neuron neuron = new Neuron(inputDimension, new ActivationFunction(), hiddenLayers, outputDimension);
        for(int i = 0; i < 1000000; i++)
        {
            neuron.forwardPropagate(input1,output1);
        }

    }
}
