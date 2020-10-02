package Avatar.Elemental.fire.homunculus;

import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.fire.Session;
import Avatar.Elemental.fire.homunculus.test.CostFunction;
import Avatar.Elemental.water.BookOfIlaan;
import Avatar.Elemental.wind.BookOfRass;

public class Neuron extends ReservoirEntity
{
    public static final int NEURON_SIZE_RETARDED = -1;
    public static final int NEURON_SIZE_VERY_SMALL = 0;
    public static final int NEURON_SIZE_SMALL = 1;
    public static final int NEURON_SIZE_MEDIUM = 2;
    public static final int NEURON_SIZE_BIG = 3;
    public static final int NEURON_SIZE_BIIIIG = 4;
    public static final int NEURON_SIZE_SINGULARITY = 5;

    int numberOfHiddenLayers;
    int[] hidden_layers_length = null;
    int[] input_activation_output_layers_length = null;
    private double[][][] hiddenLayer_From_To_ConnectionWeights;
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
                numberOfHiddenLayers = 2;
                hidden_layers_length = new int[2];
                hidden_layers_length[0] = 2;
                hidden_layers_length[1] = 2;
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
            case NEURON_SIZE_BIG:
                numberOfHiddenLayers = 100;
                hidden_layers_length = new int[numberOfHiddenLayers];
                for(int i = 0; i < numberOfHiddenLayers; i++)
                {
                    hidden_layers_length[i] = 100;
                }
                break;
            case NEURON_SIZE_BIIIIG:

                break;
            case NEURON_SIZE_SINGULARITY:
                numberOfHiddenLayers = 1000;
                hidden_layers_length = new int[numberOfHiddenLayers];
                for(int i = 0; i < numberOfHiddenLayers; i++)
                {
                    hidden_layers_length[i] = 1000000-(i*i)+outputDimension;
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

        hiddenLayer_From_To_ConnectionWeights = makeRandomInitialWeights(numberOfHiddenLayers);
        System.out.println("InitialWeights: "+ BookOfIlaan.printDouble(hiddenLayer_From_To_ConnectionWeights));
    }

    public void like(Session session, ReservoirEntity content)
    {

    }

    public void learn(double[] input, double[] correctOutput, CostFunction costFunction)
    {
    }

    public double[] propagateForward(double[] inputVector, double[] correctOutput)
    {
        // 8 byte: 128 MB ==> 4096* 4096 net
        // 128 MB ==> 16777216, also 8^8 doubles
        //
        if ( !(inputVector.length == inputDimension) )
        {
            throw new IllegalArgumentException("Wrong dimension of input Vector "+inputVector.toString()+" in propagate forward. Expected dimension "+inputDimension);
        }
        // Layer 0: INPUT.
        double[][] input_activation_output = new double[numberOfHiddenLayers+1][];
        input_activation_output[0] = new double[inputDimension];
        for(int i = 0; i < inputDimension; i++)
        {
            input_activation_output[0][i] = inputVector[i];
        }

        // We already calculated activation in first layer, so our index starts at 1
        for (int currentLayerOfDeepNeurons = 0; currentLayerOfDeepNeurons < numberOfHiddenLayers; currentLayerOfDeepNeurons++)
        {
            int currentIndexOf_Input_Activation_Output = currentLayerOfDeepNeurons+1;

            System.out.println("Now propagating DEEP Layer "+currentLayerOfDeepNeurons);
            input_activation_output[currentIndexOf_Input_Activation_Output] = new double[hidden_layers_length[currentLayerOfDeepNeurons]];

            for(int weightIndex_currentLayer = 0; weightIndex_currentLayer < input_activation_output_layers_length[currentIndexOf_Input_Activation_Output]; weightIndex_currentLayer++)
            {
                input_activation_output[currentIndexOf_Input_Activation_Output][weightIndex_currentLayer] = 0;
                for(int weightIndex_previousLayer = 0; weightIndex_previousLayer < input_activation_output_layers_length[currentLayerOfDeepNeurons]; weightIndex_previousLayer++)
                {
                    input_activation_output[currentLayerOfDeepNeurons+1][weightIndex_currentLayer]
                            += input_activation_output[currentLayerOfDeepNeurons][weightIndex_previousLayer]
                            * hiddenLayer_From_To_ConnectionWeights[currentLayerOfDeepNeurons][weightIndex_previousLayer][weightIndex_currentLayer];
                }
                input_activation_output[currentLayerOfDeepNeurons][weightIndex_currentLayer] = BookOfRass.sigmoid(input_activation_output[currentLayerOfDeepNeurons][weightIndex_currentLayer]);
            }
        }

        for(int i = 0; i < outputDimension; i++)
        {
            input_activation_output[numberOfHiddenLayers][i] = inputVector[i];
        }

        System.out.println("DONE! Input: "+ BookOfIlaan.printDouble(inputVector));
        System.out.println("OUTPUT: "+BookOfIlaan.printDouble(input_activation_output));

        double[][] DELTA_IN_NEURON = new double[numberOfHiddenLayers][];

        // Now propagate back
        // Output layer updates
        // weightUpdates[layers-1] = new double[layers_length[layers-1]];
        for(int x = 0; x < hidden_layers_length[numberOfHiddenLayers -1]; x++)
        {
            DELTA_IN_NEURON[numberOfHiddenLayers -1][x] = input_activation_output[numberOfHiddenLayers -1][x]-correctOutput[x];
        }
        
        for(int currentLayer = numberOfHiddenLayers -2; currentLayer > 0; currentLayer++)
        {
            for(int indexInCurrentLayer = 0; indexInCurrentLayer < hidden_layers_length[currentLayer]; indexInCurrentLayer++)
            {
                DELTA_IN_NEURON[currentLayer] = new double[hidden_layers_length[currentLayer]];
                // sum over all errorInNextLayer(index , element of nextLayerIndizes) * weight of the connection between
                // that layer and the current neuron in the current layer
                for(int z = 0; z < hidden_layers_length[currentLayer+1]; z++)
                {
                    DELTA_IN_NEURON[currentLayer][indexInCurrentLayer]
                        += hiddenLayer_From_To_ConnectionWeights[currentLayer][indexInCurrentLayer][indexInCurrentLayer+1]
                        * DELTA_IN_NEURON[currentLayer][z] ;
                }
            }
        }
        System.out.println("DONE. Now backwards");
        double learningRate = 0.01;

        for (int i = 0; i < numberOfHiddenLayers; i++)
        {
            for(int x = 0; x < hidden_layers_length[i]; x++)
            {
                //weights[i][x] -= learningRate*activation[i][x];
            }
        }

        return input_activation_output[numberOfHiddenLayers -1];
    }

    // w[0][0]
    private double[][][] makeRandomInitialWeights(int layers)
    {
        double[][][] seed = new double[layers+1][][];
        seed[0] = new double[inputDimension][hidden_layers_length[0]];
        for(int x = 0; x < inputDimension; x++)
        {
            for(int y = 0; y < hidden_layers_length[0]; y++)
            {
                seed[0][x][y] = Math.random()-0.5;
            }
        }

        // Final layer equals output layer. So we propagate, then take the final vector
        for (int seedLayerIndex = 1; seedLayerIndex < layers+1; seedLayerIndex++)
        {
            seed[seedLayerIndex] = new double[input_activation_output_layers_length[seedLayerIndex-1]][input_activation_output_layers_length[seedLayerIndex]];
            for(int x = 0; x < input_activation_output_layers_length[seedLayerIndex-1]; x++)
            {
                for(int y = 0; y < input_activation_output_layers_length[seedLayerIndex]; y++)
                {
                    seed[seedLayerIndex][x][y] = Math.random()-0.5;

                }
            }
        }
        return seed;
    }
}