package Avatar.Elemental.fire.AI.test;

import Avatar.Elemental.fire.AI.classic.Neuron;
import Avatar.Elemental.water.VectorSignal;
import Avatar.Elemental.wind.BookOfRass;
import org.junit.Test;

public class NeuronTest
{
    private double randomXOROutput()
    {
        return (int)(Math.random()*2);
    }

    private double[] randomXORInput()
    {
        double[] out = new double[2];
        out[0] = randomXOROutput();
        out[1] = randomXOROutput();
        return out;
    }

    private double XOR(double xIn, double yIn)
    {
        if ( xIn == 0 && yIn == 1 || yIn == 1 && xIn == 0 ) return 1;
        return 0;
    }

    @Test
    public void test()
    {
        int inputDimension = 1;
        int outputDimension = 1;

        int[] hiddenLayers = new int[1];

        for(int i = 0; i < hiddenLayers.length; i++)
        {
            hiddenLayers[i] = 2;
        }

        double[] outputData1 = new double[outputDimension];
        outputData1[0] = 1;
        double[] inputData1 = {1};

        VectorSignal input1 = new VectorSignal(inputData1);
        VectorSignal output1 = new VectorSignal(outputData1);

        Neuron neuron = new Neuron(inputDimension, BookOfRass.GELU, hiddenLayers, outputDimension);
        for(int i = 0; i < 100; i++)
        {
            neuron.forwardPropagate(input1,output1);
        }
    }
}
