package Avatar.Elemental.fire.AI.classic;

import Avatar.Elemental.water.BookOfIlaan;
import Avatar.Elemental.water.VectorSignal;
import Avatar.Elemental.wind.math.Function;

public class HiddenLayerSlice implements Propagator
{
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

    private double[] getWeights(int toNeuronIndexInNextLayer)
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
        for(int y = 0; y < deltaSignal.length(); y++)
        {
            for(int i = 0; i < getHeight(); i++)
            {
                if ( debug ) System.out.println("Excitation: "+excitation.data[i]+" // Output "+output.data[i]+"// Signal: "+deltaSignal.data[y]);
                if ( debug ) System.out.println("Old value: "+weights[y][i]);
                weights[y][i] -= 0.01*output.data[i]*deltaSignal.data[y];
                if ( debug ) System.out.println("New value: "+weights[y][i]);
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
            if ( debug ) System.out.println("Weights: "+BookOfIlaan.printDouble(getWeights(ToNeuronInNextLayerIndex)));
            if ( debug ) System.out.println("Signal: "+signal.toString());
            if ( debug ) System.out.println("Index toNeuron: "+ToNeuronInNextLayerIndex);
            outputActivations.data[ToNeuronInNextLayerIndex] = signal.scalarProduct(getWeights(ToNeuronInNextLayerIndex));
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
        double[] DELTA = new double[getHeight()];
        if ( debug ) System.out.println("Weights to output layer: "+ BookOfIlaan.printDouble(weights));
        if ( debug ) System.out.println("EXPECTED: "+ nextSlice.expectedOutput.toString());
        if ( debug ) System.out.println("RECEIVED: "+deltaSignal.toString());
        if ( debug ) System.out.println("EXCITATION: "+ nextSlice.expectedOutput.toString());
        if ( debug ) System.out.println("OUTPUT: "+ output.toString());
        double errorSize = 0;

        for(int j = 0; j < getHeight(); j++)
        {
            DELTA[j] = 0;
            // deltaSignal.length() = nextSlice.getHeight()
            for(int i = 0; i < deltaSignal.length(); i++)
            {
                System.out.println("Derivative: "+activationFunction.getDerivative(excitation.data[i]));
                DELTA[j] += (output.data[i]-nextSlice.expectedOutput.data[i])*activationFunction.getDerivative(excitation.data[i]);
            }
        }
        if ( debug ) System.out.println("DELTA VECTOR: "+BookOfIlaan.printDouble(DELTA));
        errorSize = Math.sqrt(errorSize);
        if ( firstError == 0 )
            firstError = errorSize;
        errorSize = errorSize;
        if ( debug ) System.out.println("ERROR: "+errorSize);
        if ( debug ) System.out.println("UPDATING WEIGHTS.");
        updateWeights(deltaSignal);
        if ( debug ) System.out.println("OUTPUT: "+new VectorSignal(DELTA).toString());
        return new VectorSignal(DELTA);
    }

    private VectorSignal backpropagateFromInnerLayer(VectorSignal deltaSignal)
    {
        if ( debug ) System.out.println("Weights: "+ BookOfIlaan.printDouble(weights));
        VectorSignal newErrorSignal = new VectorSignal(getHeight());
        for(int i = 0; i < newErrorSignal.length(); i++)
        {
            newErrorSignal.data[i] = 0;
            for(int x = 0; x < deltaSignal.length(); x++)
            {
                newErrorSignal.data[i] += this.getWeights(x)[i]*deltaSignal.data[x];
            }
            if ( !(excitation == null) )
                newErrorSignal.data[i] *= activationFunction.getDerivative(excitation.data[i]);
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