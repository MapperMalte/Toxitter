package Avatar.Elemental.fire.AI.classic;

import Avatar.Elemental.water.BookOfIlaan;
import Avatar.Elemental.water.VectorSignal;
import Avatar.Elemental.wind.artifacts.DiamondList;
import Avatar.Elemental.wind.math.Function;

public class Neuron
{
    protected InputLayer inputLayer;
    protected OutputLayer outputLayer;
    protected HiddenLayerSlice firstSlice;
    public DiamondList<HiddenLayerSlice> list = new DiamondList<>();

    public Neuron(int inputDimension, Function activationFunction, int[] hiddenLayerArchitecture, int outputDimension)
    {
        inputLayer = new InputLayer(inputDimension);
        list.addOnTop(new HiddenLayerSlice(inputDimension, activationFunction,this));
        list.addOnTop(new HiddenLayerSlice(inputDimension, activationFunction,this));

        for(int i = 0; i < hiddenLayerArchitecture.length; i++)
        {
            list.addOnTop(new HiddenLayerSlice(hiddenLayerArchitecture[i], activationFunction,this));
        }

        list.bottom();
        while (!list.isPointerAtTop())
        {
            HiddenLayerSlice current = list.getCurrent();
            list.next();
            HiddenLayerSlice next = list.getCurrent();
            current.setNextSlice(next,new RandomWeightSeeder());
        }

        list.top();
        HiddenLayerSlice output = new HiddenLayerSlice(outputDimension, activationFunction, this);
        list.getCurrent().setNextSlice(output,new RandomWeightSeeder());
        list.addOnTop(output);
    }

    public void forwardPropagate(VectorSignal input, VectorSignal expectedOutput)
    {
        list.top();
        list.getCurrent().setExpectedOutput(expectedOutput);
        list.bottom();
        VectorSignal current = input;
        while ( !list.getCurrent().isOutputLayer() )
        {
            current = list.getCurrent().propagate(current);
            list.next();
        }
        System.out.println(" ================================================================");
        System.out.println(" ============================= AEON =============================");
        System.out.println(" ================================================================");
        System.out.println("OUTPUT: "+ BookOfIlaan.printDouble(current.data));
        list.top();
        while (!list.getCurrent().isInputLayer())
        {
            list.previous();
            current = list.getCurrent().backPropagate(current);
        }
    }
}