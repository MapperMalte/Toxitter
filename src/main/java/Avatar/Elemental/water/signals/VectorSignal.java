package Avatar.Elemental.water.signals;


import Avatar.Elemental.water.BookOfIlaan;
import Avatar.Elemental.wind.math.Function;

public class VectorSignal extends Signal
{
    private int index = 0;
    public double[] data;

    public void put(double newData)
    {
        data[index] = newData;
        index++;
    }

    public VectorSignal(double[] input) {
        this.data = input;
    }

    public String toString()
    {
        return BookOfIlaan.printDouble(data);
    }

    public VectorSignal(int length)
    {
        data = new double[length];
    }

    public void transform(Function function)
    {
        for(int i = 0; i < data.length; i++)
        {
            data[i] = function.getValue(data[i]);
        }
    }

    public double scalarProduct(double[] weights)
    {
        double ret = 0;
        for(int i = 0; i < data.length; i++)
        {
            ret += weights[i]*data[i];
        }
        return ret;
    }

    public int length() {
        return data.length;
    }

    public VectorSignal getSubtracted(VectorSignal expectedOutput)
    {
        VectorSignal newV = new VectorSignal(length());
        for(int i = 0; i < data.length; i++)
        {
            newV.data[i] = data[i]-expectedOutput.data[i];
        }
        return newV;
    }
}