package Avatar.Elemental.water.html.annotations;

import Avatar.Elemental.water.INPUT;
import Avatar.Elemental.water.OUTPUT;

public @interface Consumes
{
    public Class<? extends OUTPUT> type();
}
