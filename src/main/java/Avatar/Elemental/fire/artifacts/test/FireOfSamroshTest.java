package Avatar.Elemental.fire.artifacts.test;

import Avatar.Elemental.fire.artifacts.FireOfSamrosh;
import Avatar.Elemental.water.BookOfIlaan;
import org.junit.Test;

public class FireOfSamroshTest
{
    @Test
    public void Dscinnsdf()
    {
        FireOfSamrosh dschinn = new FireOfSamrosh();
        dschinn.init_Homunculus(FireOfSamrosh.NEURON_SIZE_VERY_SMALL,2,3);

        double[] input = new double[2];
        input[0] = 0;
        input[1] = 1;

        double[] expectedOutput = new double[3];
        for(int i = 0; i < expectedOutput.length; i++ )
        {
            expectedOutput[i] = 0;
        }
        expectedOutput[0] = 0;
        expectedOutput[1] = 2;
        expectedOutput[2] = 150;

        for(int i = 0; i < 500000; i++)
        {
            System.out.println("ITERATION "+(i+1)+": "+ BookOfIlaan.printDouble(dschinn.propagateForward(input, expectedOutput, false)));
        }
    }
}
