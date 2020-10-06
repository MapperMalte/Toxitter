package Avatar.Elemental.fire.artifacts.test;

import Avatar.Elemental.fire.homunculus.Neuron.WeightSeeder;
import Avatar.Elemental.water.BookOfIlaan;
import org.junit.Test;

public class WeightSeederTest
{
    @Test
    public void testSeedWeights()
    {
        WeightSeeder weightSeeder = new WeightSeeder();
        int[] y = {1,3,3,2,6,1};
        System.out.println("Weights: "+ BookOfIlaan.printDouble(weightSeeder.seedRandomTensor(y)));
    }
}
