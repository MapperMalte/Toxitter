package Avatar.Elemental.fire.AI.classic.layers.hidden;

import Avatar.Elemental.fire.AI.classic.NeuralNetwork;
import Avatar.Elemental.fire.AI.classic.NeuralNetworkSpecification;
import Avatar.Elemental.water.signals.MatrixSignal;

public class Layer
{
    private MatrixSignal weights;
    private int index = 0;
    private int height;
    private NeuralNetworkSpecification neuralNetworkSpecification;

    public Layer(int height, NeuralNetworkSpecification neuralNetworkSpecification)
    {
        this.height = height;
        this.neuralNetworkSpecification = neuralNetworkSpecification;
        index = neuralNetworkSpecification.makeLayerIndex();
    }

    public void setWeights(MatrixSignal weights)
    {
        this.weights = weights;
    }

    public boolean isConnectedWith(Layer two)
    {
        return (Math.abs(two.index-index) == 1);
    }

    public boolean isNextLayerOf(Layer two)
    {
        return two.index-index == 1;
    }

    public boolean isPreviousLayerOf(Layer two)
    {
        return index-two.index == -1;
    }

    public boolean hasNextLayer()
    {
        return ( index == neuralNetworkSpecification.getWidthOfHiddenNet()-1 );
    }

    public int getIndex()
    {
        return index;
    }

    public MatrixSignal getWeights()
    {
        return this.weights;
    }

    public double getWeight(int fromNeuronIndex, int toNeuronIndex)
    {
        return weights.data[fromNeuronIndex][toNeuronIndex];
    }

    public void updateWeight(int fromNeuronIndex, int toNeuronIndex, double newValue)
    {
        this.weights.data[fromNeuronIndex][toNeuronIndex] = newValue;
    }

    public String getName() {
        return "Layer "+index;
    }

    public int getHeight() {
        return height;
    }
}