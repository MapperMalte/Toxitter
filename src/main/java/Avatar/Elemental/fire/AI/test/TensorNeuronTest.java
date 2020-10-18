package Avatar.Elemental.fire.AI.test;

import Avatar.Elemental.fire.AI.classic.graveyard.TensorNeuron;
import Avatar.Elemental.water.BookOfIlaan;
import org.junit.Test;

public class TensorNeuronTest
{
    @Test
    public void Dscinnsdf()
    {
        TensorNeuron dschinn = new TensorNeuron();
        dschinn.init_Homunculus(TensorNeuron.NEURON_SIZE_VERY_SMALL,2,3);

        double[] input = new double[2];
        input[0] = 0;
        input[1] = 1;

        double[] expectedOutput = new double[3];
        for(int i = 0; i < expectedOutput.length; i++ )
        {
            expectedOutput[i] = 0;
        }
        expectedOutput[0] = 0;
        expectedOutput[1] = 1;
        expectedOutput[2] = 1;

        for(int i = 0; i < 5; i++)
        {
            System.out.println("ITERATION "+(i+1)+": "+ BookOfIlaan.printDouble(dschinn.propagateForward(input, expectedOutput, false)));
        }
    }
}
