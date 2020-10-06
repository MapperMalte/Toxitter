package Avatar.Elemental.water.transformations.signal;

import Avatar.Elemental.water.VectorSignal;

public class Weight
{
    public void compute(VectorSignal signal, VectorSignal weights)
    {
        for (int i = 0; i < signal.data.length; i++)
        {
            signal.data[i] *= weights.data[i];
        }
    }
}
