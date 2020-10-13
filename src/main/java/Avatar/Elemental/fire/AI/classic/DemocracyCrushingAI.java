package Avatar.Elemental.fire.AI.classic;

import Avatar.Boxfresh.routes.User;
import Avatar.Elemental.earth.ReservoirEntity;
import Avatar.Elemental.earth.ReservoirEntityList;
import Avatar.Elemental.fire.AI.NeuroCocktail;
import Avatar.Elemental.fire.AI.Session;
import Avatar.Elemental.fire.AI.Stimulans_CostFunction;
import Avatar.Elemental.fire.AI.Stimulantia;

public class DemocracyCrushingAI
{
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

    }

    public void put(User user, Stimulantia stimulans)
    {

    }


    public void WANT(NeuroCocktail stimulans, Stimulans_CostFunction costFunction)
    {

    }

    public DemocracyCrushingAI()
    {
        NeuroCocktail stimulans = new NeuroCocktail();
        stimulans.put(Stimulantia.ENTROPIDIN,1);
        stimulans.put(Stimulantia.AMYGDALIN, 1);
        stimulans.put(Stimulantia.TRIBALIDIN,1);
        WANT(stimulans, new CrushDemocracyCostFunction());
    }
}
