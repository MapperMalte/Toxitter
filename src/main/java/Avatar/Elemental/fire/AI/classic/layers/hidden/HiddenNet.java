package Avatar.Elemental.fire.AI.classic.layers.hidden;

import Avatar.Elemental.fire.AI.classic.NeuralNetworkSpecification;
import Avatar.Elemental.fire.AI.classic.artifacts.BackpropagationFactory;
import Avatar.Elemental.fire.AI.classic.artifacts.WeightSeeder;
import Avatar.Elemental.fire.AI.classic.Backpropagation;
import Avatar.Elemental.water.signals.MatrixSignal;
import Avatar.Elemental.water.signals.VectorSignal;

public class HiddenNet
{
    private NeuralNetworkSpecification neuralNetworkSpecification;
    private HiddenNetPropagationAlgorithm propagationAlgorithm;
    private HiddenNetBackpropagationAlgorithm backpropagationAlgorithm;
    private BackpropagationFactory backpropagationFactory;

    private Layer[] layers;

    public Layer getFirstLayer()
    {
        return layers[0];
    }

    public HiddenNet (
            NeuralNetworkSpecification netSpecifications,
            HiddenNetPropagationAlgorithm propagationAlgorithm,
            HiddenNetBackpropagationAlgorithm backpropagationAlgorithm,
            BackpropagationFactory backpropagationFactory)
    {
        this.neuralNetworkSpecification = netSpecifications;
        this.propagationAlgorithm = propagationAlgorithm;
        this.backpropagationAlgorithm = backpropagationAlgorithm;
        this.backpropagationFactory = backpropagationFactory;
        this.layers = new Layer[netSpecifications.getHiddenLayerDimensions().length];
        for(int i = 0; i < netSpecifications.getHiddenLayerDimensions().length; i++)
        {
            this.layers[i] = new Layer(netSpecifications.getHiddenLayerDimensions()[i], neuralNetworkSpecification);
        }
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

    public Backpropagation propagate(VectorSignal input)
    {
        Backpropagation backpropagation = new BackpropagationFactory().MAKE(this.neuralNetworkSpecification);
        backpropagation.setInputIntoLayer(layers[0],input);
        for(int layerIndex = 0; layerIndex < layers.length-1; layerIndex++ )
        {

            VectorSignal interimResult = propagationAlgorithm.propagate (
                    backpropagation,
                    neuralNetworkSpecification.activationFunction,
                    layers[layerIndex],
                    layers[layerIndex+1]
            );
            System.out.println("InterimResult: "+interimResult.toString()+ " / "+backpropagation.getInputIntoLayer(layers[layerIndex+1]));
        }
        
        return backpropagation;
    }

    public void backpropagate(Backpropagation backpropagation)
    {
        for(int layerIndex = layers.length-2; layerIndex >= 0; layerIndex-- )
        {
            VectorSignal interimResult = backpropagationAlgorithm.backpropagate(
                    backpropagation,
                    neuralNetworkSpecification.activationFunction,
                    layers[layerIndex],
                    layers[layerIndex+1]);
            System.out.println("uInterimResult: "+interimResult.toString()+ " / "+backpropagation.getInputIntoLayer(layers[layerIndex+1]));
        }
    }
}