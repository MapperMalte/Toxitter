package Avatar.Elemental.water.signals;

public abstract class SignalTransformer<In extends Signal>
{
    public abstract In transform(In in);
}
