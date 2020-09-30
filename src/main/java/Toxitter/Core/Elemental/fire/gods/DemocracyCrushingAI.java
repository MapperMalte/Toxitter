package Toxitter.Core.Elemental.fire.gods;

import Toxitter.Boxfresh.routes.User;
import Toxitter.Core.Elemental.fire.NeuroCocktail;
import Toxitter.Core.Elemental.fire.Stimulantia;
import Toxitter.Core.Elemental.fire.CostFunction;
import Toxitter.Core.Elemental.fire.User_Stimulus;
import Toxitter.Core.Elemental.fire.gods.impl.CrushDemocracyCostFunction;

public class DemocracyCrushingAI
{
    public Stimulantia predict(User user, User_Stimulus stimulus)
    {
        return null;
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

    public void put(User user, Stimulantia stimulans)
    {

    }
}
