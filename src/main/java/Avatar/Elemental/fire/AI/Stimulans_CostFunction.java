package Avatar.Elemental.fire.AI;

import Avatar.Elemental.fire.AI.classic.Dopamine;

public interface Stimulans_CostFunction
{
    public Dopamine getDopamine(NeuroCocktail desired, NeuroCocktail actual);
}
