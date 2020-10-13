package Avatar.Elemental.fire.AI.classic;

import Avatar.Elemental.water.VectorSignal;

public interface Propagator
{
    public VectorSignal propagate(VectorSignal signal);
    public VectorSignal backPropagate(VectorSignal signal);
}
