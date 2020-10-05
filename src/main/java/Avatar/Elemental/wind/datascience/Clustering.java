package Avatar.Elemental.wind.datascience;

import Avatar.Elemental.water.BookOfIlaan;
import Avatar.Elemental.wind.BookOfRass;

import java.awt.print.Book;
import java.util.Arrays;

public class Clustering
{
    public void cluster(double[][] data, int samplesPerDimension)
    {
        //Indexed by DIMENSION SAMPLE instead of SAMPLE DIMENSION

        int datasetIndex = 0;
        // Assume all data to have equal length
        int dataDimension = data[0].length;
        double[] maxValues = new double[dataDimension];
        double[] minValues = new double[dataDimension];
        double[][] dataReverseIndexed = new double[dataDimension][];
        for(int dimension = 0; dimension < dataDimension; dimension++)
        {
            maxValues[dimension] = Double.MIN_VALUE;
            minValues[dimension] = Double.MAX_VALUE;
            dataReverseIndexed[dimension] = new double[data.length];
        }
        // Cant assume sorted data so we search linearly
        for (int dataIndex = 0; dataIndex < data.length; dataIndex++)
        {
            for(int dimension = 0; dimension < dataDimension; dimension++)
            {
                dataReverseIndexed[dimension][dataIndex] = data[dataIndex][dimension];
                if (data[dataIndex][dimension] > maxValues[dimension])
                {
                    maxValues[dimension] = data[dataIndex][dimension];
                }
                if (data[dataIndex][dimension] < minValues[dimension])
                {
                    minValues[dimension] = data[dataIndex][dimension];
                }
            }
        }

        System.out.println("MAX: "+ BookOfIlaan.printDouble(maxValues));
        System.out.println("MIN: "+BookOfIlaan.printDouble(minValues));

        int[][] grid = new int[dataDimension][];
        double[][] density = new double[dataDimension][];
        for(int dimension = 0; dimension < dataDimension; dimension++)
        {
            System.out.println("Min: "+minValues[dimension]+" / max "+maxValues[dimension]);
            double delta = (double)(maxValues[dimension]-minValues[dimension])/(double)samplesPerDimension;
            Arrays.sort(dataReverseIndexed[dimension]);
            System.out.println("DELTA: "+delta);
            density[dimension] = new double[samplesPerDimension];
            grid[dimension] = new int[samplesPerDimension];
            int sample = 0;
            // Initialize grid
            int sIndex = 0;

            while ( (sample < samplesPerDimension) )
            {
                density[dimension][sample] = 0;
                grid[dimension][sample] = 3;
                //System.out.println("DataSample ["+sample+"]["+dimension+"]: " +data[sample][dimension]);

                while ( dataReverseIndexed[dimension][sample+sIndex] < minValues[dimension]+delta*sample)
                {
                    //System.out.println("DataSample smaller than "+(minValues[dimension]+delta*sample));
                    density[dimension][sample]++;
                    sIndex++;
                }
                sample++;
            }
            System.out.println("sIndex: "+sIndex);
        }
        System.out.println("Grid: "+BookOfIlaan.printInt(grid));
        System.out.println("Density: "+BookOfIlaan.printDouble(density));

        // Now stochastically flow.
        for(int epoch = 0; epoch < 10; epoch++)
        {
            System.out.println("EPOCH "+epoch);
            for(int sample = 0; sample < samplesPerDimension; sample++)
            {
                double locallizedInformationalGravity = 0;
                double[] localGravityGradientInDimensionDirection = new double[dataDimension];
                for(int dimension = 0; dimension < dataDimension; dimension++)
                {
                    localGravityGradientInDimensionDirection[dimension] = 0;
                    locallizedInformationalGravity = 0;
                    if ( sample+1 < samplesPerDimension )
                    {
                        double inc = grid[dimension][sample+1]+density[dimension][sample+1]*density[dimension][sample+1];
                        locallizedInformationalGravity += inc;
                        localGravityGradientInDimensionDirection[dimension] += inc;
                    }
                    if ( sample+2 < samplesPerDimension )
                    {
                        double inc = grid[dimension][sample+1]+density[dimension][sample+2]*density[dimension][sample+2];
                        locallizedInformationalGravity += 1./4*inc;
                        localGravityGradientInDimensionDirection[dimension] += 1./4*inc;
                    }
                    if ( (sample-1) > 0 )
                    {
                        double inc = density[dimension][sample-1]+grid[dimension][sample-1];
                        locallizedInformationalGravity -= inc;
                        localGravityGradientInDimensionDirection[dimension]-= inc;
                    }
                    if ( (sample-2) > 0 )
                    {
                        double inc = density[dimension][sample-2]+grid[dimension][sample-2];
                        locallizedInformationalGravity -= 1./4*inc;
                        localGravityGradientInDimensionDirection[dimension] -= 1./4*inc;
                    }
                }
                System.out.println("LOCALLIZED GRAVITY "+locallizedInformationalGravity);
                System.out.println("VECTOR: "+BookOfIlaan.printDouble(localGravityGradientInDimensionDirection));
                // Find maximum direction
                for(int dimension = 0; dimension < dataDimension; dimension++)
                {
                    System.out.println("Grid: "+grid[dimension][sample]);
                    int strength = (int)(grid[dimension][sample]* BookOfRass.getErf(localGravityGradientInDimensionDirection[dimension]*localGravityGradientInDimensionDirection[dimension]/locallizedInformationalGravity));
                    strength = Math.abs(strength);
                    System.out.println("Strength: "+strength);
                    if ( grid[dimension][sample] < strength )
                    {
                        strength = grid[dimension][sample];
                    }
                    if ( localGravityGradientInDimensionDirection[dimension] > 0 )
                    {
                        if ( sample+1 < samplesPerDimension )
                        {
                            grid[dimension][sample] -= strength;
                            grid[dimension][sample+1] += strength;
                            System.out.println("FLOW RIGHT: "+strength);
                        }
                    }
                    if ( localGravityGradientInDimensionDirection[dimension] < 0 )
                    {
                        if ( sample-1 >= 0 )
                        {
                            grid[dimension][sample] -= strength;
                            grid[dimension][sample-1] += strength;
                            System.out.println("FLOW LEFT: "+strength);
                        }
                    }
                }
            }
        }

        System.out.println("New grid: "+BookOfIlaan.printInt(grid));
        double[] strength = new double[samplesPerDimension];

        for(int sample = 0; sample < samplesPerDimension; sample++) {
            strength[sample] = 0;
            double[] actualCoordinates = new double[dataDimension];
            for (int dimension = 0; dimension < dataDimension; dimension++)
            {
                actualCoordinates[dimension] = minValues[dimension]+((sample*(maxValues[dimension]-minValues[dimension]))/(double)samplesPerDimension);
                strength[sample] += (double)(grid[dimension][sample]*grid[dimension][sample]);
            }
            strength[sample] = Math.sqrt(strength[sample]);
            System.out.println("Sample Strength: "+strength[sample]);
            System.out.println("Actual coordinates: About "+BookOfIlaan.printDouble(actualCoordinates));
        }
    }
}