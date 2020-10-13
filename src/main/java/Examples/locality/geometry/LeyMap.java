package Examples.locality.geometry;

import Avatar.Elemental.wind.artifacts.DiamondList;

public class LeyMap
{
    private DiamondList<LeyPoint> points = new DiamondList<>();

    public void addLeypoint(LeyPoint lp)
    {
        points.addOnTop(lp);
    }

    private double dist(double x, double y)
    {
        return Math.sqrt(x*x+y*y);
    }

    public LeyPoint getCorrespondingLeyPoint(double x, double y)
    {
        double mind = Double.MAX_VALUE;
        points.bottom();
        LeyPoint found = null;

        while(!points.isPointerNull())
        {
            double dist = dist(x-points.getCurrent().x,y-points.getCurrent().y);
            if ( dist < mind )
            {
                mind = dist;
                found = points.getCurrent();
            }
            points.next();
        }

        return found;
    }
}
