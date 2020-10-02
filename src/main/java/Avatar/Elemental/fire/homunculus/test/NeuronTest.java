package Avatar.Elemental.fire.homunculus.test;

import Avatar.Elemental.fire.homunculus.Neuron;
import org.junit.Test;

public class NeuronTest
{
    @Test
    public void Dscinnsdf()
    {
        Neuron dschinn = new Neuron();
        dschinn.init_Homunculus(Neuron.NEURON_SIZE_SMALL,2,1);

        double[] input = new double[2];
        input[0] = 0;
        input[1] = 1;

        double[] expectedOutput = new double[1];
        expectedOutput[0] = 0.5;

        double[] output = dschinn.propagateForward(input, expectedOutput);
        System.out.println("OUTPUT: "+output[0]);
    }
}
