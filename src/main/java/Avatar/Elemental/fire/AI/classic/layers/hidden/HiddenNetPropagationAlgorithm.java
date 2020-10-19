package Avatar.Elemental.fire.AI.classic.layers.hidden;

import Avatar.Elemental.fire.AI.classic.Backpropagation;
import Avatar.Elemental.fire.AI.classic.artifacts.ActivationFunction;
import Avatar.Elemental.water.signals.VectorSignal;

public class HiddenNetPropagationAlgorithm
{
    public void propagate(Backpropagation backpropagation, ActivationFunction activationFunction, Layer one, Layer two)
    {
        VectorSignal output = new VectorSignal(one.getHeight());
        for(int k = 0; k < two.getHeight(); k++)
        {
            output.data[k] = 0;
            for(int i = 0; i < one.getHeight(); i++)
            {
                output.data[k] += one.getWeight(i,k)* backpropagation.getInputIntoLayer(one).data[i];
            }
            output.data[k] = activationFunction.activate(output.data[k]);
        }
        backpropagation.setOutputOfLayer(one,output);
        backpropagation.setInputIntoLayer(two,output);
    }
}
