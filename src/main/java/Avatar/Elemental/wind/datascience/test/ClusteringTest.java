package Avatar.Elemental.wind.datascience.test;

import Avatar.Elemental.water.BookOfIlaan;
import Avatar.Elemental.wind.datascience.Clustering;
import org.junit.Test;

public class ClusteringTest
{
    @Test
    public void test()
    {
        int factor = 100;
        double[][] data = new double[5*factor][10];
        for(int i = 0; i < factor; i++)
        {
            for(int x = 0; x < 10; x++)
            {
                data[i][x] = (Math.random()-0.5)*200+1000;
            }
        }
        for(int i = factor; i < 2*factor; i++)
        {
            for(int x = 0; x < 10; x++)
            {
                data[i][x] = (Math.random()-0.5)*400+1500;
            }
        }
        for(int i = 2*factor; i < 3*factor; i++)
        {
            for(int x = 0; x < 10; x++)
            {
                data[i][x] = -200;
            }
        }
        for(int i = 3*factor; i < 4*factor; i++)
        {
            for(int x = 0; x < 10; x++)
            {
                data[i][x] = (Math.random()-0.5)*200+3000;
            }
        }
        for(int i = 4*factor; i < 5*factor; i++)
        {
            for(int x = 0; x < 5; x++)
            {
                data[i][x] = (Math.random()-0.5)*200+1700;
            }
            for(int x = 6; x < 10; x++)
            {
                data[i][x] = (Math.random()-0.5)*200+700;
            }
        }
        // -400 bis 1200
        // -400+48*16
        System.out.println("Testdata: "+ BookOfIlaan.printDouble(data));
        Clustering clustering = new Clustering();
        clustering.cluster(data,100);
        // Bei 21, bei 70, bei 72
    }
}
