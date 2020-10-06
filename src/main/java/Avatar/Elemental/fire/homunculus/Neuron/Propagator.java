package Avatar.Elemental.fire.homunculus.Neuron;

import Avatar.Elemental.water.VectorSignal;

public interface Propagator
{
    public void propagate(VectorSignal signal);
    public void backPropagate(VectorSignal signal);
}
