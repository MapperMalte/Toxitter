package Avatar.Elemental.fire.AI.classic.graveyard;

import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.fire.AI.Session;
import Avatar.Elemental.fire.AI.classic.RandomWeightSeeder;
import Avatar.Elemental.fire.AI.test.CostFunction;
import Avatar.Elemental.water.BookOfIlaan;
import Avatar.Elemental.wind.BookOfRass;

public class TensorNeuron extends ReservoirEntity
{
    public static final int NEURON_SIZE_RETARDED = -1;
    public static final int NEURON_SIZE_VERY_SMALL = 0;
    public static final int NEURON_SIZE_SMALL = 1;
    public static final int NEURON_SIZE_MEDIUM = 2;
    public static final int NEURON_SIZE_BIG = 3;
    public static final int NEURON_SIZE_VERY_BIG = 5;

    int numberOfHiddenLayers;
    int[] hidden_layers_length = null;
    int[] input_activation_output_layers_length = null;
    private double[][][] input_hiddenLayer_output_FromToConnectionWeights;
    private double[] output;
    private int inputDimension;
    private int outputDimension;

    public void init_Homunculus(int size, int inputDimension, int outputDimension)
    {
        // Wenn inputDimension größer als 10^6, vorher Faltung vorschalten, falls proximityYieldsInformation
        this.inputDimension = inputDimension;
        this.outputDimension = outputDimension;
        switch (size)
        {
            case NEURON_SIZE_RETARDED:
                numberOfHiddenLayers = 1;
                hidden_layers_length = new int[1];
                hidden_layers_length[0] = 2;
                break;
            case NEURON_SIZE_VERY_SMALL:
                numberOfHiddenLayers = 5;
                hidden_layers_length = new int[numberOfHiddenLayers];
                for(int i = 0; i < numberOfHiddenLayers; i++)
                {
                    hidden_layers_length[i] = 10;
                }
                break;
            case NEURON_SIZE_SMALL:
                numberOfHiddenLayers = 20;
                hidden_layers_length = new int[numberOfHiddenLayers];
                for(int i = 0; i < numberOfHiddenLayers; i++)
                {
                    hidden_layers_length[i] = 50;
                }
                break;
            case NEURON_SIZE_MEDIUM:
                numberOfHiddenLayers = 50;
                hidden_layers_length = new int[numberOfHiddenLayers];
                for(int i = 0; i < numberOfHiddenLayers; i++)
                {
                    hidden_layers_length[i] = 500;
                }
                break;
            case NEURON_SIZE_BIG:
                numberOfHiddenLayers = 1000;
                hidden_layers_length = new int[numberOfHiddenLayers];
                for(int i = 0; i < numberOfHiddenLayers; i++)
                {
                    hidden_layers_length[i] = 1000;
                }
                break;
            case NEURON_SIZE_VERY_BIG:
                numberOfHiddenLayers = 10000;
                hidden_layers_length = new int[numberOfHiddenLayers];
                for(int i = 0; i < numberOfHiddenLayers; i++)
                {
                    hidden_layers_length[i] = 10000;
                }
                break;
            default:
                numberOfHiddenLayers = 10;
                hidden_layers_length = new int[10];
                break;
        }
        input_activation_output_layers_length = new int[hidden_layers_length.length+2];
        input_activation_output_layers_length[0] = inputDimension;
        for(int i = 0; i < numberOfHiddenLayers; i++)
        {
            input_activation_output_layers_length[i+1] = hidden_layers_length[i];
        }
        input_activation_output_layers_length[input_activation_output_layers_length.length-1] = outputDimension;

        input_hiddenLayer_output_FromToConnectionWeights = new RandomWeightSeeder().seedRandomTensor(input_activation_output_layers_length);
        System.out.println("InitialWeights: "+ BookOfIlaan.printDouble(input_hiddenLayer_output_FromToConnectionWeights));
    }

    public void like(Session session, ReservoirEntity content)
    {

    }

    public void learn(double[] input, double[] correctOutput, CostFunction costFunction)
    {
    }

    public double[] propagateForward(double[] inputVector, double[] correctOutput, boolean debug)
    {
        // 8 byte: 128 MB ==> 4096* 4096 net
        // 128 MB ==> 16777216, also 8^8 doubles
        //
        if ( !(inputVector.length == inputDimension) )
        {
            throw new IllegalArgumentException("Wrong dimension of input Vector "+inputVector.toString()+" in propagate forward. Expected dimension "+inputDimension);
        }
        // Layer 0: INPUT.
        double[][] input_activation = new double[numberOfHiddenLayers+1][];
        double[] output = new double[outputDimension];
        input_activation[0] = new double[inputDimension];
        for(int i = 0; i < inputDimension; i++)
        {
            input_activation[0][i] = inputVector[i];
        }

        // We already calculated activation in first layer, so our index starts at 1
        for (int currentLayerOfDeepNeurons = 0; currentLayerOfDeepNeurons < numberOfHiddenLayers; currentLayerOfDeepNeurons++)
        {
            int currentIndexOf_Input_Activation_Output = currentLayerOfDeepNeurons+1;
            input_activation[currentIndexOf_Input_Activation_Output] = new double[hidden_layers_length[currentLayerOfDeepNeurons]];
            for(int weightIndex_currentLayer = 0; weightIndex_currentLayer < hidden_layers_length[currentLayerOfDeepNeurons]; weightIndex_currentLayer++)
            {
                input_activation[currentIndexOf_Input_Activation_Output][weightIndex_currentLayer] = 0;
                for(int weightIndex_previousLayer = 0; weightIndex_previousLayer < input_activation_output_layers_length[currentLayerOfDeepNeurons]; weightIndex_previousLayer++)
                {
                    input_activation[currentIndexOf_Input_Activation_Output][weightIndex_currentLayer]
                            += input_activation[currentLayerOfDeepNeurons][weightIndex_previousLayer]
                            * input_hiddenLayer_output_FromToConnectionWeights[currentLayerOfDeepNeurons][weightIndex_previousLayer][weightIndex_currentLayer];
                }
                input_activation[currentIndexOf_Input_Activation_Output][weightIndex_currentLayer] = BookOfRass.GELU.getValue(input_activation[currentIndexOf_Input_Activation_Output][weightIndex_currentLayer]);
            }
        }

        for(int outputLayerIndex = 0; outputLayerIndex < outputDimension; outputLayerIndex++)
        {
            output[outputLayerIndex] = 0;
            for(int lastLayerIndex = 0; lastLayerIndex < hidden_layers_length[hidden_layers_length.length-1]; lastLayerIndex++)
            {
                output[outputLayerIndex]
                        += input_activation[input_activation.length-1][lastLayerIndex]
                        * input_hiddenLayer_output_FromToConnectionWeights[input_hiddenLayer_output_FromToConnectionWeights.length-1][lastLayerIndex][outputLayerIndex];
            }
        }

        if ( debug ) System.out.println("DONE! Input: "+ BookOfIlaan.printDouble(inputVector));
        if ( debug ) System.out.println("ACTIVATION: "+BookOfIlaan.printDouble(input_activation));
        if ( debug ) System.out.println("OUTPUT: "+BookOfIlaan.printDouble(output));
        double[][] DELTA_IN_NEURON_PLUS_OUTPUT = new double[numberOfHiddenLayers+1][];
        DELTA_IN_NEURON_PLUS_OUTPUT[numberOfHiddenLayers] = new double[outputDimension];
        for(int x = 0; x < outputDimension; x++)
        {
            DELTA_IN_NEURON_PLUS_OUTPUT[numberOfHiddenLayers][x] = (output[x]-correctOutput[x])*BookOfRass.GELU.getDerivative(output[x]);
        }
        
        for(int currentLayer = numberOfHiddenLayers-1; currentLayer >= 0; currentLayer--)
        {
            DELTA_IN_NEURON_PLUS_OUTPUT[currentLayer] = new double[hidden_layers_length[currentLayer]];
            for(int indexInCurrentLayer = 0; indexInCurrentLayer < input_activation_output_layers_length[currentLayer]; indexInCurrentLayer++)
            {
                // sum over all errorInNextLayer(index , element of nextLayerIndizes) * weight of the connection between
                // that layer and the current neuron in the current layer
                if ( debug ) System.out.println("Outll: "+input_activation_output_layers_length[currentLayer+2]);
                DELTA_IN_NEURON_PLUS_OUTPUT[currentLayer][indexInCurrentLayer] = 0;
                for(int indexInNextLayer = 0; indexInNextLayer < input_activation_output_layers_length[currentLayer+2]; indexInNextLayer++)
                {
                    if ( debug ) System.out.println("currentLayer: "+currentLayer);
                    if ( debug ) System.out.println("indexInCurrentLayer: "+indexInCurrentLayer+" running to "+input_activation_output_layers_length[currentLayer+1] );
                    if ( debug ) System.out.println("indexInNextLayer: "+indexInNextLayer+" running to "+input_activation_output_layers_length[currentLayer+2]);
                    if ( debug ) System.out.println("hiddenLayer_From_To_ConnectionWeights: "+ input_hiddenLayer_output_FromToConnectionWeights[currentLayer][indexInCurrentLayer][indexInNextLayer]);
                    if ( debug ) System.out.println("DELTA_IN_NEXT_NEURO: "+DELTA_IN_NEURON_PLUS_OUTPUT[currentLayer+1][indexInNextLayer]);
                    DELTA_IN_NEURON_PLUS_OUTPUT[currentLayer][indexInCurrentLayer]
                        += input_hiddenLayer_output_FromToConnectionWeights[currentLayer][indexInCurrentLayer][indexInNextLayer]
                        * DELTA_IN_NEURON_PLUS_OUTPUT[currentLayer+1][indexInNextLayer] ;
                }
                DELTA_IN_NEURON_PLUS_OUTPUT[currentLayer][indexInCurrentLayer]*=BookOfRass.GELU.getDerivative(input_activation[currentLayer][indexInCurrentLayer]);
                //System.out.println("NEW DELTA "+DELTA_IN_NEURON_PLUS_OUTPUT[currentLayer][indexInCurrentLayer]);
            }
        }
        if ( debug ) System.out.println("DONE");
        if ( debug ) System.out.println("Expected: "+BookOfIlaan.printDouble(correctOutput));
        if ( debug ) System.out.println("DONE. Deltas: ");
        if ( debug ) System.out.println(BookOfIlaan.printDouble(DELTA_IN_NEURON_PLUS_OUTPUT));
        if ( debug ) System.out.println("Old weights: "+BookOfIlaan.printDouble(input_hiddenLayer_output_FromToConnectionWeights));
        double learningRate = 0.06;

        for (int currentLayerOfDeepNeurons = 0; currentLayerOfDeepNeurons < numberOfHiddenLayers; currentLayerOfDeepNeurons++)
        {
            int currentIndexOf_Input_Activation_Output = currentLayerOfDeepNeurons+1;
            input_activation[currentIndexOf_Input_Activation_Output] = new double[hidden_layers_length[currentLayerOfDeepNeurons]];
            for(int weightIndex_currentLayer = 0; weightIndex_currentLayer < hidden_layers_length[currentLayerOfDeepNeurons]; weightIndex_currentLayer++)
            {
                //input_activation[currentIndexOf_Input_Activation_Output][weightIndex_currentLayer] = 0;

                for(int weightIndex_previousLayer = 0; weightIndex_previousLayer < input_activation_output_layers_length[currentLayerOfDeepNeurons]; weightIndex_previousLayer++)
                {
                    double a = input_hiddenLayer_output_FromToConnectionWeights[currentLayerOfDeepNeurons][weightIndex_previousLayer][weightIndex_currentLayer];
                    //System.out.println("MESODELTA "+learningRate*input_activation[currentLayerOfDeepNeurons][weightIndex_previousLayer]*DELTA_IN_NEURON[currentLayerOfDeepNeurons][weightIndex_currentLayer]);
                    input_hiddenLayer_output_FromToConnectionWeights[currentLayerOfDeepNeurons][weightIndex_previousLayer][weightIndex_currentLayer]
                            -= learningRate
                            * input_activation[currentLayerOfDeepNeurons][weightIndex_previousLayer]
                            * DELTA_IN_NEURON_PLUS_OUTPUT[currentLayerOfDeepNeurons][weightIndex_currentLayer];
                    double b = input_hiddenLayer_output_FromToConnectionWeights[currentLayerOfDeepNeurons][weightIndex_previousLayer][weightIndex_currentLayer];
                    //System.out.println("MESODELTA: "+(b-a)+" / "+currentLayerOfDeepNeurons+" / "+weightIndex_previousLayer+" / "+weightIndex_currentLayer);
                }
            }
        }

        if ( debug ) System.out.println("New weights: "+BookOfIlaan.printDouble(input_hiddenLayer_output_FromToConnectionWeights));

        return output;
    }
}