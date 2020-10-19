package Avatar.Elemental.fire.AI.classic;

import Avatar.Elemental.fire.AI.classic.artifacts.BackpropagationFactory;
import Avatar.Elemental.fire.AI.classic.artifacts.WeightSeeder;
import Avatar.Elemental.fire.AI.classic.layers.hidden.HiddenNet;
import Avatar.Elemental.fire.AI.classic.layers.hidden.HiddenNetBackpropagationAlgorithm;
import Avatar.Elemental.fire.AI.classic.layers.hidden.HiddenNetPropagationAlgorithm;
import Avatar.Elemental.fire.AI.classic.layers.hidden.Layer;
import Avatar.Elemental.fire.AI.classic.exceptions.IllegalInputDimensionException;
import Avatar.Elemental.water.signals.MatrixSignal;
import Avatar.Elemental.water.signals.VectorSignal;

public class NeuralNetwork
{
    private NeuralNetworkSpecification specification;
    private Layer inputLayer;
    private HiddenNet hiddenNet;
    private Layer outputLayer;
    private WeightSeeder weightSeeder;

    private void createFromSpec()
    {
        this.inputLayer = new Layer(this);
        if ( specification.hasHiddenLayers() )
        {
            MatrixSignal weights = new MatrixSignal(weightSeeder.seedRandomMatrix(specification.getInputDimension(), specification.getLayerHeight(0)));
            inputLayer.setWeights(weights);
        } else {
            MatrixSignal weights = new MatrixSignal(weightSeeder.seedRandomMatrix(specification.getInputDimension(), specification.getOutputDimension() ));
        }

        hiddenNet = new HiddenNet(
                specification,
                new HiddenNetPropagationAlgorithm(),
                new HiddenNetBackpropagationAlgorithm(),
                new BackpropagationFactory());
        hiddenNet.seed(weightSeeder);
    }

    public NeuralNetwork(NeuralNetworkSpecification specification, WeightSeeder weightSeeder)
    {
        this.specification = specification;
    }

    public VectorSignal predict(VectorSignal input)
    {
        if ( !(input.length() == specification.getInputDimension()) )
        {
            throw new IllegalInputDimensionException("Input vector signal should have dimension "+specification.getInputDimension()+" but has dimension "+input.length());
        }
        Backpropagation backpropagation = new Backpropagation();





        return null;
    }
}
