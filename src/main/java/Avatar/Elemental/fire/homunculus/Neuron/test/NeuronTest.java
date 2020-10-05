package Avatar.Elemental.fire.homunculus.Neuron.test;

import Avatar.Elemental.fire.homunculus.Neurino;
import org.junit.Test;

public class NeuronTest
{
    @Test
    public void Dscinnsdf()
    {
        Neurino dschinn = new Neurino();
        dschinn.init_Homunculus(Neurino.NEURON_SIZE_RETARDED,2,2);

        double[] input = new double[2];
        input[0] = 0;
        input[1] = 1;

        double[] expectedOutput = new double[2];
        expectedOutput[0] = 0.5;
        expectedOutput[1] = 0.5;

        double[] output = dschinn.propagateForward(input, expectedOutput);
        System.out.println("OUTPUT: "+output[0]);
    }
}
