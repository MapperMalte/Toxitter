package Avatar.Elemental.fire.AI.classic.layers.hidden;

import Avatar.Elemental.fire.AI.classic.Backpropagation;
import Avatar.Elemental.fire.AI.classic.artifacts.ActivationFunction;
import Avatar.Elemental.fire.AI.classic.exceptions.IllegalyConnectedLayersException;
import Avatar.Elemental.water.signals.VectorSignal;

public class HiddenNetBackpropagationAlgorithm
{
    public void backpropagate(Backpropagation backpropagation, ActivationFunction activationFunction, Layer one, Layer two)
    {
        if ( !one.isConnectedWith(two) )
        {
            throw new IllegalyConnectedLayersException("Layer "+one.getName()+" is not connected to "+two.getName());
        }
        if ( !one.isPreviousLayerOf(two) )
        {
            throw new IllegalyConnectedLayersException("Layer "+one.getName()+" should be previous layer of "+two.getName());
        }
        VectorSignal newDelta = new VectorSignal(one.getHeight());
        for(int i = 0; i < one.getHeight(); i++)
        {
            newDelta.data[i] = 0;
            for(int k = 0; k < two.getHeight(); k++)
            {
                newDelta.data[i] += one.getWeight(i,k)* backpropagation.getDelta(two,k);
            }
            newDelta.data[i] *= activationFunction.activateDerivative(backpropagation.getOutputOfLayer(one).data[i]);
        }
        backpropagation.putDelta(one, newDelta);
    }
}
