package Examples.locality.geometry;

import Toxitter.Elemental.wind.DiamondList;

public class LeyPoint {
    public double x;
    public double y;
    public String colorHex;

    public LeyPoint(double x, double y, String colorHex)
    {
        this.x = x;
        this.y = y;
        this.colorHex = colorHex;
    }

    private DiamondList<LeyPoint> neighbours = new DiamondList<>();

    public void addNeighbour(LeyPoint p)
    {
        if ( !neighbours.contains(p) )
        {
            neighbours.addOnTop(p);
            System.out.println("Leypoint at "+x+"/"+y+" is connected with LeyPoint "+p.x+"/"+p.y);
        }
    }

    public boolean neighbourOf(LeyPoint p)
    {
        if ( p == null ) return false;
        return neighbours.contains(p) || p.equals(this);
    }
}
