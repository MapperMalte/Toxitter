package Avatar.Elemental.fire.AI;

import Avatar.Boxfresh.routes.User;
import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.earth.ReservoirEntityList;
import Avatar.Elemental.wind.BookOfRass;

public abstract class ContentAI
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

    public void WANT(NeuroCocktail stimulans, Stimulans_CostFunction costFunction)
    {

    }
}