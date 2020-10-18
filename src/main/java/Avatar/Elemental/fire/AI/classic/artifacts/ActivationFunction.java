package Avatar.Elemental.fire.AI.classic.artifacts;

import Avatar.Elemental.wind.BookOfRass;

public class ActivationFunction
{
    public double activate(double x)
    {
        return BookOfRass.sigmoid(x);
    }

    public double activateDerivative(double x)
    {
        return BookOfRass.getSigmoidDerivative(x);
    }
}
