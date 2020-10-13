package Avatar.Elemental.fire.AI.test;

import Avatar.Elemental.fire.AI.classic.RandomWeightSeeder;
import Avatar.Elemental.water.BookOfIlaan;
import org.junit.Test;

public class WeightSeederTest
{
    @Test
    public void testSeedWeights()
    {
        RandomWeightSeeder weightSeeder = new RandomWeightSeeder();
        int[] y = {1,3,3,2,6,1};
        System.out.println("Weights: "+ BookOfIlaan.printDouble(weightSeeder.seedRandomTensor(y)));
    }
}
