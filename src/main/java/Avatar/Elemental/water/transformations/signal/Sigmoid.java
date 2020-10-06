package Avatar.Elemental.water.transformations.signal;

import Avatar.Elemental.water.VectorSignal;
import Avatar.Elemental.wind.BookOfRass;

public class Sigmoid
{
    public void transform(VectorSignal input)
    {
        for(int i = 0; i < input.length(); i++)
        {
            input.data[i] = BookOfRass.sigmoid(input.data[i]);
        }
    }
}
