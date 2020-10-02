package Avatar.Elemental.fire;

import Avatar.Elemental.fire.classical.Dopamine;

public interface Stimulans_CostFunction
{
    public Dopamine getDopamine(NeuroCocktail desired, NeuroCocktail actual);
}
