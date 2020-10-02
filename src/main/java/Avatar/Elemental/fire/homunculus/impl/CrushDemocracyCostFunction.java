package Avatar.Elemental.fire.homunculus.impl;

import Avatar.Elemental.fire.Stimulans_CostFunction;
import Avatar.Elemental.fire.classical.Dopamine;
import Avatar.Elemental.fire.NeuroCocktail;

import static Avatar.Elemental.fire.Stimulantia.*;

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