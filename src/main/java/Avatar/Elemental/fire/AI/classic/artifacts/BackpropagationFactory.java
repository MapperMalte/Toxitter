package Avatar.Elemental.fire.AI.classic.artifacts;

import Avatar.Elemental.fire.AI.classic.Backpropagation;
import Avatar.Elemental.fire.AI.classic.NeuralNetworkSpecification;

public class BackpropagationFactory {
    public Backpropagation MAKE(NeuralNetworkSpecification neuralNetworkSpecification)
    {
        return new Backpropagation(neuralNetworkSpecification);
    }
}
