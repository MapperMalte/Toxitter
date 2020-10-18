package Avatar.Elemental.fire.AI.classic.galance.hidden;

import Avatar.Elemental.fire.AI.classic.artifacts.ActivationFunction;

public class NeuralNetworkSpecification
{
    public ActivationFunction activationFunction;
    private int inputDimension;
    private int[] hiddenLayerDimensions;
    private int outputDimension;

    public NeuralNetworkSpecification()
    {

    }

    public boolean hasHiddenLayers()
    {
        return ( !(hiddenLayerDimensions == null) && hiddenLayerDimensions.length > 0 );
    }

    public int getWidth()
    {
        return hiddenLayerDimensions.length;
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