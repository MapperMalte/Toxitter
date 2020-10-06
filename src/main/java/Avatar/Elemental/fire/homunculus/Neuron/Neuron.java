package Avatar.Elemental.fire.homunculus.Neuron;

import Avatar.Elemental.water.VectorSignal;
import Avatar.Elemental.wind.DiamondList;

public class Neuron
{
    protected InputLayer inputLayer;
    protected OutputLayer outputLayer;
    protected HiddenLayerSlice firstSlice;
    public DiamondList<HiddenLayerSlice> list = new DiamondList<>();

    public Neuron(int inputDimension, ActivationFunction activationFunction, int[] hiddenLayerArchitecture, int outputDimension)
    {
        inputLayer = new InputLayer(inputDimension);

        for(int i = 0; i < hiddenLayerArchitecture.length; i++)
        {
            list.addOnTop(new HiddenLayerSlice(hiddenLayerArchitecture[i], new ActivationFunction(),this));
        }

        list.bottom();
        while (!list.isPointerAtTop())
        {
            HiddenLayerSlice current = list.getCurrent();
            list.next();
            HiddenLayerSlice next = list.getCurrent();
            current.setNextSlice(next,new WeightSeeder());
        }

        list.top();
        HiddenLayerSlice output = new HiddenLayerSlice(outputDimension, new ActivationFunction(), this);
        list.getCurrent().setNextSlice(output,new WeightSeeder());
        list.addOnTop(output);
    }

    public void forwardPropagate(VectorSignal input, VectorSignal expectedOutput)
    {
        list.top();
        list.getCurrent().setExpectedOutput(expectedOutput);
        list.bottom();
        list.getCurrent().propagate(input);
    }
}