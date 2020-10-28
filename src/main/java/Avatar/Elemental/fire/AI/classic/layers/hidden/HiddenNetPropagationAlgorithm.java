package Avatar.Elemental.fire.AI.classic.layers.hidden;

import Avatar.Elemental.fire.AI.classic.Backpropagation;
import Avatar.Elemental.water.signals.VectorSignal;
import Avatar.Elemental.wind.math.Function;

public class HiddenNetPropagationAlgorithm
{
    public VectorSignal propagate(Backpropagation backpropagation,
                                  Function activationFunction,
                                  Layer one,
                                  Layer two)
    {
        VectorSignal output = new VectorSignal(one.getHeight());
        for(int k = 0; k < two.getHeight(); k++)
        {
            output.data[k] = 0;
            for(int i = 0; i < one.getHeight(); i++)
            {
                output.data[k] += one.getWeight(i,k)* backpropagation.getInputIntoLayer(one).data[i];
            }
            output.data[k] = activationFunction.getValue(output.data[k]);
        }
        backpropagation.setOutputOfLayer(one,output);
        backpropagation.setInputIntoLayer(two,output);
        if ( !two.hasNextLayer() )
        {
            backpropagation.setActualOutput(output);
        }
        return output;
    }
}
