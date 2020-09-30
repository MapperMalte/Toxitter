package Toxitter.Elemental.fire.gods.impl;

import Toxitter.Elemental.fire.CostFunction;
import Toxitter.Elemental.fire.Dopamine;
import Toxitter.Elemental.fire.NeuroCocktail;

import static Toxitter.Elemental.fire.Stimulantia.*;

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