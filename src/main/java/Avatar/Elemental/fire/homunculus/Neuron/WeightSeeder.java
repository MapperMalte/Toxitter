package Avatar.Elemental.fire.homunculus.Neuron;

public class WeightSeeder
{
    public double[][][] seedRandomTensor(int[] y)
    {
        double[][][] data = new double[y.length-1][][];
        for (int i = 0; i < y.length-1; i++)
        {
            data[i] = new double[y[i]][y[i+1]];
            System.out.println("Layer "+y[i]+" next "+y[i+1]);
            System.out.println("Data "+i+": "+data[i].length);
            for(int x = 0; x < y[i]; x++)
            {
                data[i][x] = new double[y[i+1]];
                for (int z = 0; z < y[i+1]; z++)
                {
                    data[i][x][z] = Math.random()-0.5;
                    System.out.println("ixz: "+i+"/"+x+"/"+z+": "+data[i][x][z]);
                }
            }
        }
        /*
        data[y.length-1] = new double[y[y.length-2]][y[y.length-1]];
        for(int x = 0; x < y[y.length-2]; x++)
        {
            for (int z = 0; z < y[y.length-1]; z++)
            {
                data[y.length-1][x][z] = Math.random()-0.5;
                System.out.println("2ixz: "+(y.length-1)+"/"+x+"/"+z+": "+data[y.length-1][x][z]);
            }
        }*/
        return data;
    }
}
