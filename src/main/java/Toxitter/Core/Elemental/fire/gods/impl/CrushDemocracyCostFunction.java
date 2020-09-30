package Toxitter.Core.Elemental.fire.gods.impl;

import Toxitter.Core.Elemental.fire.CostFunction;
import Toxitter.Core.Elemental.fire.Dopamine;
import Toxitter.Core.Elemental.fire.NeuroCocktail;

import static Toxitter.Core.Elemental.fire.Stimulantia.*;

public class CrushDemocracyCostFunction implements CostFunction
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