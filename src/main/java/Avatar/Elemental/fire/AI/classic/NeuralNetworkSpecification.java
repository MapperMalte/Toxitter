package Avatar.Elemental.fire.AI.classic;

import Avatar.Elemental.fire.AI.classic.layers.hidden.Layer;
import Avatar.Elemental.wind.math.Function;

public class NeuralNetworkSpecification
{
    public Function activationFunction;
    private int inputDimension;
    private int[] hiddenLayerDimensions;
    private int outputDimension;
    private int runningId = -1;

    public int makeLayerIndex()
    {
        runningId++;
        return runningId;
    }

    public NeuralNetworkSpecification()
    {

    }
    public void setActivationFunction(Function function)
    {
        this.activationFunction = function;
    }

    public boolean hasHiddenLayers()
    {
        return ( !(hiddenLayerDimensions == null) && hiddenLayerDimensions.length > 0 );
    }

    public int getWidthOfHiddenNet()
    {
        return hiddenLayerDimensions.length;
    }

    public int getWidthOfHiddenNetAndOutputLayer()
    {
        return hiddenLayerDimensions.length+1;
    }

    public int getWidthWithInputAndOutputLayers()
    {
        return hiddenLayerDimensions.length +2 ;
    }

    public NeuralNetworkSpecification(int inputDimension, int[] hiddenLayerDimensions, int outputDimension)
    {
        this.inputDimension = inputDimension;
        this.hiddenLayerDimensions = hiddenLayerDimensions;
        this.outputDimension = outputDimension;
    }

    public int getLayerHeight(Layer layer)
    {
        return hiddenLayerDimensions[layer.getIndex()];
    }

    public int getLayerHeight(int index)
    {
        return hiddenLayerDimensions[index];
    }

    public void setInputDimension(int inputDimension)
    {
        this.inputDimension = inputDimension;
    }

    public void setHiddenLayerDimensions(int[] hiddenLayerDimensions)
    {
        this.hiddenLayerDimensions = hiddenLayerDimensions;
    }

    public void setOutputDimension(int dimension)
    {
        this.outputDimension = dimension;
    }

    public int getInputDimension() {
        return inputDimension;
    }

    public int[] getHiddenLayerDimensions() {
        return hiddenLayerDimensions;
    }

    public int getOutputDimension() {
        return outputDimension;
    }
}