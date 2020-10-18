package Avatar.Elemental.fire.AI.classic.galance.hidden;

import Avatar.Elemental.fire.AI.classic.artifacts.ActivationFunction;
import Avatar.Elemental.fire.AI.classic.artifacts.WeightSeeder;
import Avatar.Elemental.water.signals.MatrixSignal;

public class HiddenNetSpecifications
{
    private int[] layerHeights;
    public ActivationFunction activationFunction;

    public HiddenNetSpecifications(int[] layerHeights)
    {
        this.layerHeights = layerHeights;
    }

    public int getLayerHeight(Layer layer)
    {
        return layerHeights[layer.getIndex()];
    }
}