package Avatar.Elemental.fire.AI.classic;

import Avatar.Elemental.fire.AI.Stimulans_CostFunction;
import Avatar.Elemental.fire.AI.NeuroCocktail;

import static Avatar.Elemental.fire.AI.Stimulantia.*;

public class CrushDemocracyCostFunction implements Stimulans_CostFunction
{
    @Override
    public Dopamine getDopamine(NeuroCocktail desired, NeuroCocktail actual)
    {
        double d = desired.get(DOPAMINE)-actual.get(DOPAMINE);
        d = d*d;
        double c = desired.get(GABA)-actual.get(GABA);
        c = c*c;
        return new Dopamine(Math.sqrt(d+c));
    }
}