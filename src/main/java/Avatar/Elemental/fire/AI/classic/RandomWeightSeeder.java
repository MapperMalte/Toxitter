package Avatar.Elemental.fire.AI.classic;

import Avatar.Elemental.fire.AI.classic.artifacts.WeightSeeder;

public class RandomWeightSeeder implements WeightSeeder
{
    private double amplitude = 3;

    public double[][] seedRandomMatrix(int xL, int yL)
    {
        double[][] randomMatrix = new double[xL][yL];
        for(int x = 0; x < xL; x++)
        {
            for(int y = 0; y < yL; y++)
            {
                randomMatrix[x][y] = (Math.random())*amplitude;
            }
        }
        return randomMatrix;
    }

    public double[][][] seedRandomTensor(int[] y)
    {
        double[][][] data = new double[y.length-1][][];
        for (int i = 0; i < y.length-1; i++)
        {
            data[i] = new double[y[i]][y[i+1]];
            for(int x = 0; x < y[i]; x++)
            {
                data[i][x] = new double[y[i+1]];
                for (int z = 0; z < y[i+1]; z++)
                {
                    data[i][x][z] = (Math.random()-0.5);
                }
            }
        }
        return data;
    }
}
