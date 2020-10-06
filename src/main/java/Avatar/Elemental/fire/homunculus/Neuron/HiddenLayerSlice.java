package Avatar.Elemental.fire.homunculus.Neuron;

import Avatar.Elemental.water.BookOfIlaan;
import Avatar.Elemental.water.VectorSignal;
import Avatar.Elemental.wind.BookOfRass;

import java.awt.print.Book;

public class HiddenLayerSlice implements Propagator
{
    public static final boolean debug = false;
    private int height = 0;
    private double[][] weights;
    private ActivationFunction activationFunction;
    private HiddenLayerSlice nextSlice = null;
    private HiddenLayerSlice previousSlice = null;
    private Neuron parent;
    private VectorSignal expectedOutput;
    public double[] excitation;

    public void setExpectedOutput(VectorSignal expectedOutput)
    {
        if ( debug ) System.out.println("Sertrr "+expectedOutput.toString()+" / "+isOutputLayer());
        this.expectedOutput = expectedOutput;
    }

    private int getHeight()
    {
        //bksdf
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

    public void setNextSlice(HiddenLayerSlice nextSlice, WeightSeeder weightSeeder)
    {
        this.weights = weightSeeder.seedRandomMatrix(getHeight(),nextSlice.getHeight(),10);
        if ( debug ) System.out.println("Wwegiths: "+ BookOfIlaan.printDouble(this.weights));
        this.nextSlice = nextSlice;
        this.nextSlice.previousSlice = this;
    }

    public boolean isOutputLayer()
    {
        return (nextSlice==null);
    }

    public HiddenLayerSlice(int width, ActivationFunction activationFunction, Neuron parent)
    {
        this.parent = parent;
        this.height = width;
    }

    @Override
    public void propagate(VectorSignal signal)
    {
        excitation = signal.data;
        if ( debug ) System.out.println("PROPAGATE "+signal.toString());
        if ( isOutputLayer() )
        {
            System.out.println("Prediction: "+signal.toString());
            backPropagate(signal);
        } else
        {
            VectorSignal outputActivations = new VectorSignal(nextSlice.getHeight());
            for(int ToNeuronInNextLayerIndex = 0; ToNeuronInNextLayerIndex < outputActivations.length(); ToNeuronInNextLayerIndex++)
            {
                outputActivations.data[ToNeuronInNextLayerIndex] = new VectorSignal(getWeights(ToNeuronInNextLayerIndex)).scalarProduct(signal.data);
            }
            if ( debug ) System.out.println("Pretransform: "+BookOfIlaan.printDouble(outputActivations.data));
            outputActivations.transform(BookOfRass.Sigmoid);
            if ( debug ) System.out.println("Post: "+outputActivations.toString());
            nextSlice.propagate(outputActivations);
        }
    }

    private HiddenLayerSlice getPreviousSlice()
    {
        return this.previousSlice;
    }

    @Override
    public void backPropagate(VectorSignal signal)
    {
        if ( debug ) System.out.println("Backpropagating error: "+signal.toString());
        if ( this.previousSlice == null )
        {
            if ( debug ) System.out.println("PREVIOUS SLICE NULL! AGAIN IN FIRST LAYER");
            return;
        }
        double[] error = new double[getHeight()];

        if ( isOutputLayer() )
        {
            VectorSignal errorSignal = new VectorSignal(getHeight());
            for(int i = 0; i < getHeight(); i++)
            {
                errorSignal.data[i] = (signal.data[i]-expectedOutput.data[i])*BookOfRass.getSigmoidDerivative(signal.data[i]);
            }
            previousSlice.backPropagate(errorSignal);
        } else {
            if ( debug ) System.out.println("Weights: "+ BookOfIlaan.printDouble(weights));
            VectorSignal newErrorSignal = new VectorSignal(getHeight());
            for(int i = 0; i < getHeight(); i++)
            {
                newErrorSignal.data[i] = 0;
                for(int x = 0; x < nextSlice.getHeight(); x++)
                {
                    newErrorSignal.data[i] += weights[i][x]*signal.data[x];
                }
                if ( !(excitation == null) )
                    newErrorSignal.data[i] *= excitation[i]*(1-excitation[i]);
                for(int x = 0; x < nextSlice.getHeight(); x++)
                {
                    if ( debug ) System.out.println("DELTA "+1 * newErrorSignal.data[i] * excitation[x]);
                    weights[i][x] -= 1 * newErrorSignal.data[i] * excitation[x];
                }
            }
            previousSlice.backPropagate(newErrorSignal);
        }
    }
}