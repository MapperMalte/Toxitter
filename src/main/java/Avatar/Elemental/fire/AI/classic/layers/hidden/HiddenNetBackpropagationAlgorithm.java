package Avatar.Elemental.fire.AI.classic.layers.hidden;

import Avatar.Elemental.fire.AI.classic.Backpropagation;
import Avatar.Elemental.fire.AI.classic.exceptions.IllegalOperationOrderException;
import Avatar.Elemental.fire.AI.classic.exceptions.IllegalyConnectedLayersException;
import Avatar.Elemental.water.signals.VectorSignal;
import Avatar.Elemental.wind.math.Function;

public class HiddenNetBackpropagationAlgorithm
{
    public VectorSignal backpropagate(Backpropagation backpropagation, Function activationFunction, Layer one, Layer two)
    {
        System.out.println("From "+one.getName()+" to "+two.getName());
        if ( !one.isConnectedWith(two) )
        {
            throw new IllegalyConnectedLayersException("Layer "+one.getName()+" is not connected to "+two.getName());
        }
        if ( !one.isPreviousLayerOf(two) )
        {
            throw new IllegalyConnectedLayersException("Layer "+one.getName()+" should be previous layer of "+two.getName());
        }
        if ( !two.hasNextLayer() )
        {
            if ( !backpropagation.hasAlreadyCalculatedOutputSignal() )
            {
                throw new IllegalOperationOrderException("Backpropagate should not be started from output layer, unless the net output has been calculated before");
            }
            VectorSignal deltaInOutputLayer = backpropagation.getActualOutput().getSubtracted(backpropagation.getExpectedInOutputLayer());
            backpropagation.putDelta(two,deltaInOutputLayer);
        }
        VectorSignal newDelta = new VectorSignal(one.getHeight());
        for(int i = 0; i < one.getHeight(); i++)
        {
            newDelta.data[i] = 0;
            for(int k = 0; k < two.getHeight(); k++)
            {
                newDelta.data[i] += one.getWeight(i,k)* backpropagation.getDelta(two,k);
            }
            newDelta.data[i] *= activationFunction.getDerivative(backpropagation.getOutputOfLayer(one).data[i]);
        }
        backpropagation.putDelta(one, newDelta);
        return newDelta;
    }
}
