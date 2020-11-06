package Avatar.Elemental.water.html.annotations;

import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.water.INPUT;
import Avatar.Elemental.water.OUTPUT;

public @interface Produces
{
    public Class<? extends INPUT> type();
}
