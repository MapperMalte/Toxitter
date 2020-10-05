package Avatar.Elemental.fire.homunculus.Neuron;

public class Neuron
{
    protected InputLayer inputLayer;
    protected OutputLayer outputLayer;

    public void setInputLayer(InputLayer inputLayer)
    {
        this.inputLayer = inputLayer;
    }

    public InputLayer getInputLayer()
    {
        return this.inputLayer;
    }

    public void setOutputLayer(OutputLayer outputLayer)
    {
        this.outputLayer = outputLayer;
    }

    public OutputLayer getOutputLayer()
    {
        return this.outputLayer;
    }
}