package Avatar.Elemental.wind.math.arcane;

import Avatar.Elemental.wind.BookOfRass;

public class UnitSphere
{
    private int dimension = 0;

    public UnitSphere(int dimensions)
    {
        this.dimension = dimensions;
    }

    public static double getVolume(double dimension)
    {
        return Math.pow(Math.PI,(double)dimension/2)/ BookOfRass.GammaFunction.getValue(1+(double)dimension/2);
    }

    public double getSurfaceArea()
    {
        return (dimension+1)*getVolume(dimension+1);
    }

    public void project()
    {

    }
}
