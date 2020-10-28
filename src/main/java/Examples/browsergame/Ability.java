package Examples.browsergame;

public abstract class Ability
{
    static double[] cooldown_per_level;
    static double[] mana_cost_per_level;

    int level;
    long currentCooldownStart;

    public abstract void execute(AbilityContext abilityContext);

    public abstract void forUnit(Unit u);
}
