package Avatar.Elemental.fire.AI.classic;

import Avatar.Elemental.water.VectorSignal;

public class InputLayer
{
    protected HiddenLayerSlice hiddenLayer;
    private VectorSignal value;

    public InputLayer(int dimension)
    {

    }

    public void setHiddenLayer(HiddenLayerSlice hiddenLayer)
    {
        this.hiddenLayer = hiddenLayer;
    }

    public HiddenLayerSlice getHiddenLayer(HiddenLayerSlice hiddenLayer)
    {
        return this.hiddenLayer;
    }

    public VectorSignal getValue()
    {
        return this.value;
    }

    public synchronized void forwardPropagate(double[] input)
    {
        this.value = new VectorSignal(input);
    }
}
