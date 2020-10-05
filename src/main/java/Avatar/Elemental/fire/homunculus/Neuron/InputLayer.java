package Avatar.Elemental.fire.homunculus.Neuron;

import Avatar.Elemental.water.Signal;

public class InputLayer
{
    protected HiddenLayer hiddenLayer;
    private Signal value;

    public InputLayer(int dimension)
    {

    }

    public void setHiddenLayer(HiddenLayer hiddenLayer)
    {
        this.hiddenLayer = hiddenLayer;
    }

    public HiddenLayer getHiddenLayer(HiddenLayer hiddenLayer)
    {
        return this.hiddenLayer;
    }

    public Signal getValue()
    {
        return this.value;
    }

    public synchronized void forwardPropagate(double[] input)
    {
        this.value = new Signal(input);
        hiddenLayer.setInputLayer(this);
        hiddenLayer.propagate();
    }
}
