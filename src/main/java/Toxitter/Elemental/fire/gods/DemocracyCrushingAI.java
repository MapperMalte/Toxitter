package Toxitter.Elemental.fire.gods;

import Toxitter.Boxfresh.routes.User;
import Toxitter.Elemental.earth.ReservoirEntity;
import Toxitter.Elemental.earth.ReservoirEntityList;
import Toxitter.Elemental.fire.*;
import Toxitter.Elemental.fire.gods.impl.CrushDemocracyCostFunction;

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


    public void WANT(NeuroCocktail stimulans, CostFunction costFunction)
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
