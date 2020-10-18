package Avatar.Elemental.wind.artifacts.gems;

import Avatar.Elemental.water.signals.VectorSignal;

public class Sum
{
    public double compute(VectorSignal signal)
    {
        double sum = 0;
        for(int i = 0; i < signal.data.length; i++)
        {
            sum += signal.data[i];
        }
        return sum;
    }
}
