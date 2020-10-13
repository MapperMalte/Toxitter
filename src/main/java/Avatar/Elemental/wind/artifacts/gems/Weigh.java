package Avatar.Elemental.wind.artifacts.gems;

import Avatar.Elemental.water.VectorSignal;

public class Weigh extends ManyToMany
{
    public VectorSignal transform(VectorSignal signal, VectorSignal weights)
    {
        for (int i = 0; i < signal.data.length; i++)
        {
            signal.data[i] *= weights.data[i];
        }
        return signal;
    }
}
