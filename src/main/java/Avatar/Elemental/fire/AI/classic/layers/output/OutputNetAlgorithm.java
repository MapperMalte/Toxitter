package Avatar.Elemental.fire.AI.classic.layers.output;

import Avatar.Elemental.fire.AI.classic.artifacts.OutputActivationFunction;
import Avatar.Elemental.fire.AI.classic.artifacts.WeightSeeder;
import Avatar.Elemental.fire.AI.classic.Backpropagation;
import Avatar.Elemental.fire.AI.classic.layers.hidden.Layer;
import Avatar.Elemental.fire.AI.classic.exceptions.IllegalOperationOrderException;
import Avatar.Elemental.fire.AI.classic.exceptions.IllegalyConnectedLayersException;
import Avatar.Elemental.water.signals.MatrixSignal;
import Avatar.Elemental.water.signals.VectorSignal;

public class OutputNetAlgorithm
{
    private OutputActivationFunction outputActivationFunction;
    private MatrixSignal weights;
    private Layer lastLayer;
    private Layer outputLayer;

    public OutputNetAlgorithm(Layer lastLayer, WeightSeeder weightSeeder)
    {
        this.lastLayer = lastLayer;
    }

    public void propagate(Backpropagation backpropagation)
    {
        if ( lastLayer.hasNextLayer() )
        {
            throw new IllegalyConnectedLayersException("LastLayer of OutputNetAlgorithm should actually be a last layer.");
        }
        VectorSignal output = new VectorSignal(outputLayer.getHeight());
        for(int h = 0; h < outputLayer.getHeight(); h++)
        {
            output.data[h] = 0;
            for(int l = 0; l < lastLayer.getHeight(); l++)
            {
                output.data[h] += lastLayer.getWeight(l,h) * backpropagation.getOutputOfLayer(lastLayer).data[l];
            }
        }
        outputActivationFunction.transform(output);
    }

    public void backpropagate(Backpropagation backpropagation)
    {
        if ( !backpropagation.hasAlreadyCalculatedOutputSignal() )
        {
            throw new IllegalOperationOrderException("Backpropagate should not be started from output layer, unless the net output has been calculated before");
        }
        VectorSignal deltaInOutputLayer = backpropagation.getActualOutput().getSubtracted(backpropagation.getExpectedInOutputLayer());
        VectorSignal deltaInLayerBeforeOutputLayer = new VectorSignal(lastLayer.getHeight());
        for(int i = 0; i < lastLayer.getHeight(); i++)
        {
            deltaInLayerBeforeOutputLayer.data[i] = 0;
        }
    }
}