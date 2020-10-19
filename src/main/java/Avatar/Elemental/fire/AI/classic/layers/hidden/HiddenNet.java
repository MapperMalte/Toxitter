package Avatar.Elemental.fire.AI.classic.layers.hidden;

import Avatar.Elemental.fire.AI.classic.NeuralNetwork;
import Avatar.Elemental.fire.AI.classic.NeuralNetworkSpecification;
import Avatar.Elemental.fire.AI.classic.artifacts.BackpropagationFactory;
import Avatar.Elemental.fire.AI.classic.artifacts.WeightSeeder;
import Avatar.Elemental.fire.AI.classic.Backpropagation;
import Avatar.Elemental.fire.AI.classic.exceptions.IllegalyConnectedLayersException;
import Avatar.Elemental.water.signals.MatrixSignal;
import Avatar.Elemental.water.signals.VectorSignal;

public class HiddenNet
{
    private NeuralNetworkSpecification hiddenNetSpecifications;
    private HiddenNetPropagationAlgorithm propagationAlgorithm;
    private HiddenNetBackpropagationAlgorithm backpropagationAlgorithm;
    private BackpropagationFactory backpropagationFactory;

    private Layer[] layers;

    public Layer getFirstLayer()
    {
        return layers[0];
    }

    public HiddenNet (
            NeuralNetworkSpecification hiddenNetSpecifications,
            HiddenNetPropagationAlgorithm propagationAlgorithm,
            HiddenNetBackpropagationAlgorithm backpropagationAlgorithm,
            BackpropagationFactory backpropagationFactory)
    {
        this.hiddenNetSpecifications = hiddenNetSpecifications;
        this.propagationAlgorithm = propagationAlgorithm;
        this.backpropagationAlgorithm = backpropagationAlgorithm;
        this.backpropagationFactory = backpropagationFactory;
    }

    public void seed(WeightSeeder weightSeeder)
    {
        for(int layerIndex = 0; layerIndex < layers.length-1; layerIndex++ )
        {
            layers[layerIndex].setWeights(
                new MatrixSignal(
                    weightSeeder.seedRandomMatrix(
                    layers[layerIndex].getHeight(),
                    layers[layerIndex+1].getHeight())
                )
            );
        }
    }

    public VectorSignal propagate(VectorSignal input)
    {
        Backpropagation backpropagation = backpropagationFactory.MAKE();
        for(int layerIndex = 0; layerIndex < layers.length-1; layerIndex++ )
        {
            propagationAlgorithm.propagate(
                    backpropagation,
                    hiddenNetSpecifications.activationFunction,
                    layers[layerIndex], layers[layerIndex+1]);
        }
        return backpropagation.getActualOutput();
    }

    public void backpropagate(VectorSignal errorSignalFromOutputLayer)
    {

    }
}