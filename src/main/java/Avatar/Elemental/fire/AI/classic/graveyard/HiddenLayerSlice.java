package Avatar.Elemental.fire.AI.classic.graveyard;

import Avatar.Elemental.fire.AI.classic.artifacts.Propagator;
import Avatar.Elemental.fire.AI.classic.RandomWeightSeeder;
import Avatar.Elemental.water.BookOfIlaan;
import Avatar.Elemental.water.signals.VectorSignal;
import Avatar.Elemental.wind.math.Function;

public class HiddenLayerSlice implements Propagator
{
    public static final double LEARNING_RATE = 0.01;
    public static int layers = 0;
    public static double firstError = 0;
    public static final boolean debug = true;
    private int height = 0;
    private double[][] weights;
    private Function activationFunction;
    private HiddenLayerSlice nextSlice = null;
    private HiddenLayerSlice previousSlice = null;
    private Neuron parent;
    private int layer;
    private VectorSignal expectedOutput;
    public VectorSignal excitation;
    public VectorSignal output;

    public HiddenLayerSlice(int width, Function activationFunction, Neuron parent)
    {
        this.parent = parent;
        this.height = width;
        this.activationFunction = activationFunction;
        this.layer = layers;
        layers++;
    }

    public void setExpectedOutput(VectorSignal expectedOutput)
    {
        if ( debug ) System.out.println("Sertrr "+expectedOutput.toString()+" / "+isOutputLayer());
        this.expectedOutput = expectedOutput;
    }

    private int getHeight()
    {
        return height;
    }
    private int getMaximumNeuronIndex()
    {
        return weights.length-1;
    }

    private double[] getWeightsToNeuronInNextLayer(int toNeuronIndexInNextLayer)
    {
        return weights[toNeuronIndexInNextLayer];
    }

    public void setNextSlice(HiddenLayerSlice nextSlice, RandomWeightSeeder weightSeeder)
    {
        this.weights = weightSeeder.seedRandomMatrix(nextSlice.getHeight(), getHeight());
        if ( debug ) System.out.println("Seeded Weights: "+ BookOfIlaan.printDouble(this.weights));
        this.nextSlice = nextSlice;
        this.nextSlice.previousSlice = this;
    }

    public boolean isOutputLayer()
    {
        return (nextSlice==null);
    }

    public boolean isInputLayer()
    {
        return (previousSlice == null);
    }

    private void updateWeights(VectorSignal deltaSignal)
    {
        System.out.println("UPDATE WEIGHTS received "+deltaSignal.toString()+" / "+deltaSignal.length()+" / "+getHeight());
        for(int y = 0; y < deltaSignal.length(); y++)
        {
            System.out.println("DeltaSignal-Index "+y+" / value: "+deltaSignal.data[y]);
            for(int i = 0; i < getHeight(); i++)
            {
                if ( debug ) System.out.println("Excitation: "+excitation.data[i]+" // Output "+output.data[i]+"// Signal: "+deltaSignal.data[y]);
                if ( debug ) System.out.println("Weights to Neuron "+y+" in next layer: "+ BookOfIlaan.printDouble(getWeightsToNeuronInNextLayer(y)));
                getWeightsToNeuronInNextLayer(y)[i] -= LEARNING_RATE*output.data[i]*getWeightsToNeuronInNextLayer(y)[i];
                if ( debug ) System.out.println("New value: "+getWeightsToNeuronInNextLayer(y)[i]);
            }
        }
    }

    private void propagateFromLayerBeforeOutputLayer()
    {

    }

    @Override
    public VectorSignal propagate(VectorSignal signal)
    {
        if ( debug ) System.out.println("=========================== Propagate =================================");
        this.excitation = signal;
        if ( !isInputLayer() )
            this.previousSlice.output = signal;
        if ( debug ) System.out.println("LAYER "+(layer+1)+": ");
        if ( debug ) System.out.println("PROPAGATE "+signal.toString());
        if ( debug ) System.out.println("Own weights: "+BookOfIlaan.printDouble(weights));
        VectorSignal outputActivations;
        outputActivations = new VectorSignal(nextSlice.getHeight());
        for(int ToNeuronInNextLayerIndex = 0; ToNeuronInNextLayerIndex < outputActivations.length(); ToNeuronInNextLayerIndex++)
        {
            if ( debug ) System.out.println("Weights: "+BookOfIlaan.printDouble(getWeightsToNeuronInNextLayer(ToNeuronInNextLayerIndex)));
            if ( debug ) System.out.println("Signal: "+signal.toString());
            if ( debug ) System.out.println("Index toNeuron: "+ToNeuronInNextLayerIndex);
            outputActivations.data[ToNeuronInNextLayerIndex] = signal.scalarProduct(getWeightsToNeuronInNextLayer(ToNeuronInNextLayerIndex));
            if ( debug ) System.out.println("Activation: in Neuron "+ToNeuronInNextLayerIndex+" of next layer: "+outputActivations.data[ToNeuronInNextLayerIndex]);
        }
        if (nextSlice.isOutputLayer())
        {
            //System.out.println("Output: "+signal.toString());
        }
        if ( debug ) System.out.println("Pretransform: "+BookOfIlaan.printDouble(outputActivations.data));
        if ( !isInputLayer() )
            outputActivations.transform(activationFunction);
        //if ( nextSlice.isOutputLayer() )
        if ( debug ) System.out.println("Post: "+outputActivations.toString());
        nextSlice.excitation = outputActivations;
        if (nextSlice.isOutputLayer() )
            output = excitation;
        return outputActivations;
    }

    private HiddenLayerSlice getPreviousSlice()
    {
        return this.previousSlice;
    }

    private VectorSignal getDelta()
    {
        return null;
    }

    private void calculcateDeltas()
    {

    }


    /**
     * The output layer serves only as representation. We will only access its height and its expected value.
     * Considering the rest, it's just a dummy
     * @param deltaSignal
     * @return
     */
    private VectorSignal backpropagateFromLayerBeforeOutputLayer(VectorSignal deltaSignal)
    {
        if ( debug ) System.out.println("IN OUTPUT LAYER");
        double[] DELTA_HERE = new double[nextSlice.getHeight()];
        if ( debug ) System.out.println("Weights to output layer: "+ BookOfIlaan.printDouble(weights));
        if ( debug ) System.out.println("EXPECTED: "+ nextSlice.expectedOutput.toString());
        if ( debug ) System.out.println("RECEIVED: "+deltaSignal.toString());
        if ( debug ) System.out.println("DIFFERENCE: "+deltaSignal.getSubtracted(nextSlice.expectedOutput).toString());
        if ( debug ) System.out.println("EXCITATION: "+ excitation.toString());
        if ( debug ) System.out.println("OUTPUT: "+ output.toString());
        double errorSize = 0;
        for(int j = 0; j < nextSlice.getHeight(); j++)
        {
            DELTA_HERE[j] = -(output.data[j]-nextSlice.expectedOutput.data[j])*activationFunction.getDerivative(nextSlice.excitation.data[j]);
            System.out.println("Derivative: "+activationFunction.getDerivative(nextSlice.excitation.data[j]));
        }
        deltaSignal = new VectorSignal(DELTA_HERE);
        if ( debug ) System.out.println("DELTA VECTOR: "+BookOfIlaan.printDouble(DELTA_HERE));
        if ( debug ) System.out.println("ERROR: "+errorSize);
        if ( debug ) System.out.println("UPDATING WEIGHTS.");
        updateWeights(deltaSignal);

        double[] DELTA_TO_PROPAGATA_BACK = new double[getHeight()];
        for(int j = 0; j < getHeight(); j++)
        {
            DELTA_TO_PROPAGATA_BACK[j] = 0;
            for(int k = 0; k < nextSlice.getHeight(); k++)
            {
                DELTA_TO_PROPAGATA_BACK[j] += DELTA_HERE[k]*getWeightsToNeuronInNextLayer(k)[j];
            }
            DELTA_TO_PROPAGATA_BACK[j] *= activationFunction.getDerivative(excitation.data[j]);
            System.out.println("Derivative next: "+activationFunction.getDerivative(excitation.data[j]));
        }
        deltaSignal = new VectorSignal(DELTA_TO_PROPAGATA_BACK);
        if ( debug ) System.out.println("OUTPUT: "+deltaSignal.toString());
        return deltaSignal;
    }

    private VectorSignal backpropagateFromInnerLayer(VectorSignal deltaSignal)
    {
        if ( debug ) System.out.println("BACKPROPAGATE FROM INNER LAYER");
        if ( debug ) System.out.println("Received signal: "+deltaSignal.toString());
        if ( debug ) System.out.println("Weights: "+ BookOfIlaan.printDouble(weights));
        VectorSignal newErrorSignal = new VectorSignal(getHeight());
        for(int i = 0; i < getHeight(); i++)
        {
            newErrorSignal.data[i] = 0;
            for(int x = 0; x < deltaSignal.length(); x++)
            {
                newErrorSignal.data[i] += this.getWeightsToNeuronInNextLayer(x)[i]*deltaSignal.data[x];
            }
            if ( !(excitation == null) )
            {
                System.out.println("Derivative: "+activationFunction.getDerivative(excitation.data[i]));
                newErrorSignal.data[i] *= activationFunction.getDerivative(excitation.data[i]);
            } else
            {
                System.out.println("Null excitation");
            }

        }
        updateWeights(deltaSignal);
        if ( debug ) System.out.println("Weights after update: "+ BookOfIlaan.printDouble(weights));
        if ( debug ) System.out.println("OUTPUT: "+newErrorSignal.toString());
        return newErrorSignal;
    }

    /**
     * Fills THIS Layer with Delta values and updates weights
     * @param deltaSignal
     * @return
     */
    @Override
    public VectorSignal backPropagate(VectorSignal deltaSignal)
    {
        if ( debug ) System.out.println("============================ Backpropagate ================================");
        if ( debug ) System.out.println("Layer "+(layer+1));
        if ( debug ) System.out.println("Excitation: "+excitation.toString());
        if ( debug ) System.out.println("Backpropagating signal: "+deltaSignal.toString());
        if ( this.previousSlice == null )
        {
            if ( debug ) System.out.println("PREVIOUS SLICE NULL! AGAIN IN FIRST LAYER");
        }
        if ( nextSlice.isOutputLayer() )
        {
            return backpropagateFromLayerBeforeOutputLayer(deltaSignal);
        } else
        {
            return backpropagateFromInnerLayer(deltaSignal);
        }
    }

    public HiddenLayerSlice getNextSlice() {
        return nextSlice;
    }
}