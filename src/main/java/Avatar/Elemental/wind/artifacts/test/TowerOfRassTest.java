package Avatar.Elemental.wind.artifacts.test;

import Avatar.Elemental.water.VectorSignal;
import Avatar.Elemental.wind.artifacts.ComputationGraph;
import Avatar.Elemental.wind.artifacts.gems.Sigmoid;
import Avatar.Elemental.wind.artifacts.gems.Sum;
import Avatar.Elemental.wind.artifacts.gems.Weigh;

public class TowerOfRassTest
{
    public void graph()
    {
        ComputationGraph forward = new ComputationGraph();
        forward.expects(VectorSignal.class);
        forward.returns(10);
        forward.convergeNext();
            forward.enqueue(new Weigh());
            forward.enqueue(new Sum());
            forward.enqueue(new Sigmoid());
        forward.split();
        forward.output();

        ComputationGraph backward = new ComputationGraph();
        backward.expects(VectorSignal.class);
        backward.returns(4);
        backward.convergeNext();
            backward.enqueue(new Weigh());
    }
}
