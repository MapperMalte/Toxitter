package Avatar.Elemental.fire.AI.classic.galance.hidden;

import Avatar.Elemental.fire.AI.classic.artifacts.WeightSeeder;
import Avatar.Elemental.fire.AI.classic.galance.hidden.exceptions.IllegalyConnectedLayersException;
import Avatar.Elemental.water.signals.VectorSignal;

public class HiddenNet
{
    private HiddenNetSpecifications hiddenNet;
    private Backpropagation backpropagation;
    private HiddenNetSpecifications hiddenNetSpecifications;
    private Layer[] layers;

    public HiddenNet(HiddenNetSpecifications hiddenNetSpecifications)
    {
        this.hiddenNet = hiddenNetSpecifications;
        this.backpropagation = new Backpropagation();
    }

    public void seed(WeightSeeder weightSeeder)
    {

    }

    public void backpropagate(Layer one, Layer two)
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
            newDelta.data[i] *= hiddenNet.activationFunction.activateDerivative(backpropagation.getOutputOfLayer(one).data[i]);
        }
        backpropagation.putDelta(one, newDelta);
    }

    public void propagate(Layer one, Layer two)
    {
        VectorSignal output = new VectorSignal(one.getHeight());
        for(int k = 0; k < two.getHeight(); k++)
        {
            output.data[k] = 0;
            for(int i = 0; i < one.getHeight(); i++)
            {
                output.data[k] += one.getWeight(i,k)* backpropagation.getInputIntoLayer(one).data[i];
            }
            output.data[k] = hiddenNet.activationFunction.activate(output.data[k]);
        }
        backpropagation.setOutputOfLayer(one,output);
        backpropagation.setInputIntoLayer(two,output);
    }
}