package Avatar.Elemental.water.signals;

public class MatrixSignal extends Signal
{
    public double[][] data;

    public MatrixSignal(int m, int n)
    {
        this.data = new double[m][n];
    }

    public MatrixSignal(double[][] data)
    {
        this.data = data;
    }
}