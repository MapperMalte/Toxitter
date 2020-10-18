package Avatar.Elemental.fire.AI.classic.galance.hidden.test;

import Avatar.Elemental.fire.AI.classic.RandomWeightSeeder;
import Avatar.Elemental.fire.AI.classic.galance.hidden.HiddenNetSpecifications;
import Avatar.Elemental.fire.AI.classic.galance.hidden.NeuralNetwork;
import Avatar.Elemental.fire.AI.classic.galance.hidden.NeuralNetworkSpecification;
import org.junit.Test;

public class NeuralNetworkTest
{
    @Test
    public void test()
    {
        NeuralNetworkSpecification spec = new NeuralNetworkSpecification();
        spec.setHiddenLayerDimensions(null);
        spec.setInputDimension(1);
        spec.setOutputDimension(1);

        NeuralNetwork neuralNetwork = new NeuralNetwork(spec, new RandomWeightSeeder());
    }
}