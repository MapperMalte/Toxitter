package Avatar.Elemental.fire.homunculus.Neuron;

import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.water.Signal;

public class HiddenLayer extends ReservoirEntity
{
    private float[][][] weights;
    private ActivationFunction activationFunction;
    private InputLayer inputLayer;

    public HiddenLayer(int width, int[] layer_length, ActivationFunction activationFunction)
    {

    }

    public void setInputLayer(InputLayer inputLayer)
    {
        this.inputLayer = inputLayer;
    }

    public void propagate()
    {
        Signal s = inputLayer.getValue();
    }

    public void backpropagate()
    {

    }
}
