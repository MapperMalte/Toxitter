package Avatar.Elemental.water.html;

import Avatar.Elemental.earth.Reservoir;
import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.water.DataAccessToReservoirEntity;
import Avatar.Elemental.water.html.annotations.Card;

public class HtmlPress
{
    public HtmlPress()
    {

    }

    public void put(ReservoirEntity reservoirEntity)
    {
        DataAccessToReservoirEntity dataAccessToReservoirEntity = new DataAccessToReservoirEntity(reservoirEntity);

        if ( reservoirEntity.getClass().isAnnotationPresent(Card.class) )
        {

        }
    }
}
