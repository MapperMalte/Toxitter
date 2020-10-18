package Avatar.Elemental.fire.AI.classic.galance.hidden;

import Avatar.Elemental.water.signals.VectorSignal;

public class Backpropagation
{
    public VectorSignal[] DELTA_OF_LAYER;
    private VectorSignal[] inputIntoLayers;
    private VectorSignal[] outputOfLayers;
    private VectorSignal inputIntoInputLayer;
    private VectorSignal expectedInOutputLayer;
    private VectorSignal actualOutput = null;

    public VectorSignal getExpectedInOutputLayer()
    {
        return expectedInOutputLayer;
    }

    public boolean hasAlreadyCalculatedOutputSignal()
    {
        return !(getActualOutput() == null);
    }

    public VectorSignal getActualOutput()
    {
        return this.actualOutput;
    }

    public VectorSignal getInputIntoLayer(Layer layer)
    {
        return inputIntoLayers[layer.getIndex()];
    }

    public VectorSignal getOutputOfLayer(Layer layer)
    {
        return outputOfLayers[layer.getIndex()];
    }

    public VectorSignal getDeltaOfLayer(Layer layer)
    {
        return DELTA_OF_LAYER[layer.getIndex()];
    }

    public void putDelta(Layer layer, VectorSignal delta)
    {
        DELTA_OF_LAYER[layer.getIndex()] = delta;
    }

    public double getDelta(Layer layer, int index)
    {
        return DELTA_OF_LAYER[layer.getIndex()].data[index];
    }

    public void setOutputOfLayer(Layer one, VectorSignal output)
    {
        outputOfLayers[one.getIndex()] = output;
    }

    public void setInputIntoLayer(Layer one, VectorSignal input)
    {
        inputIntoLayers[one.getIndex()] = input;
    }
}