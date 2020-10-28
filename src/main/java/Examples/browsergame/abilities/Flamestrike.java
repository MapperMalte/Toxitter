package Examples.browsergame.abilities;

import Examples.browsergame.Ability;
import Examples.browsergame.AbilityContext;
import Examples.browsergame.Unit;

public class Flamestrike extends Ability
{

    @Override
    public void execute(AbilityContext abilityContext) {
        abilityContext.getPossibleTargets().foreach(this);
    }

    @Override
    public void forUnit(Unit u) {

    }
}
