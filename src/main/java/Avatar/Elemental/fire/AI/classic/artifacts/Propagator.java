package Avatar.Elemental.fire.AI.classic.artifacts;

import Avatar.Elemental.water.signals.VectorSignal;

public interface Propagator
{
    public VectorSignal propagate(VectorSignal signal);
    public VectorSignal backPropagate(VectorSignal signal);
}
