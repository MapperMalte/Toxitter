package Toxitter.Elemental.fire;

import Toxitter.Boxfresh.routes.User;
import Toxitter.Elemental.earth.ReservoirEntity;
import Toxitter.Elemental.earth.ReservoirEntityList;
import Toxitter.Elemental.wind.BookOfRass;
import org.tensorflow.Graph;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;

import java.io.UnsupportedEncodingException;

public abstract class AI
{
    private static class Neuron
    {

    }

    private static class Layer
    {
        Layer previousLayer;
        Layer nextLayer;

        double[] inputWeights;

        double computeActivationFunction()
        {
            return BookOfRass.relu(90);
        }
    }


    NeuroCocktail currentState = new NeuroCocktail();

    public NeuroCocktail predict(User user, ReservoirEntity stimulus)
    {
        return null;
    }

    public NeuroCocktail predict(User user, ReservoirEntityList stimuli)
    {
        return null;
    }

    public void stimulate(Session session, User user, Stimulantia stimulantia, double value)
    {
        currentState.put(stimulantia,value);

    }

    public void WANT(NeuroCocktail stimulans, CostFunction costFunction)
    {

    }
}