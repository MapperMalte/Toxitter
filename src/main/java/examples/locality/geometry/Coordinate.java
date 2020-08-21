package examples.locality.geometry;

import Toxitter.Model.ReservoirEntity;

public class Coordinate extends ReservoirEntity<Integer>
{
    private double x;
    private double y;

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer key) {

    }
}
