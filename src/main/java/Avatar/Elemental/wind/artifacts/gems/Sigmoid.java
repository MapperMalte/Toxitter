package Avatar.Elemental.wind.artifacts.gems;

import Avatar.Elemental.water.signals.VectorSignal;
import Avatar.Elemental.wind.BookOfRass;

public class Sigmoid extends Gem {
    public void transform(VectorSignal input)
    {
        for(int i = 0; i < input.length(); i++)
        {
            input.data[i] = BookOfRass.sigmoid(input.data[i]);
        }
    }
}
