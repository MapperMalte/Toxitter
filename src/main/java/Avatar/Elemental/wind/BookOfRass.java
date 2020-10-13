package Avatar.Elemental.wind;

import Avatar.Elemental.wind.math.Function;
import Avatar.Elemental.wind.math.functions.*;

public class BookOfRass
{
    private static final double p1 = 1/Math.sqrt(2*Math.PI);
    public static Function Sigmoid = new Sigmoid();
    public static Function GELU = new GELU();
    public static Function RELU = new LeakyRELU();
    public static FastGauß FastGauß = new FastGauß();
    public static GammaFunction GammaFunction = new GammaFunction();
    /**
     * Quickly returns approximate values for a skewed normal distribution with expectation 0, variance 1 and skew parameter lambda.
     * Where g(z) = 2 * \phi(z)Phi(\lambda z), where \phi(z) is the normal distribution and \Phi(z) its CDF
     * @param lambda
     * @param x
     * @return
     */
    public static double getSkewNormalDistributionPDF(double lambda, double x)
    {
        if ( x < -3/lambda ) return 0;
        if ( -3/lambda <= x && x < -1/lambda )
        {
            return 1/8.*p1*Math.exp(-x*x/2)*(9*lambda*x+3*lambda*lambda*x*x+1/3.*lambda*lambda*lambda*x*x*x+9);
        }
        if ( -1/lambda <= x && x < 1/lambda )
        {
            return 1/4.*p1*Math.exp(-x*x/2)*(9*lambda*x-3*lambda*lambda*x*x+1/3.*lambda*lambda*lambda+7);
        }
        if ( 3/lambda <= x )
        {
            return Math.sqrt(2/Math.PI)*Math.exp((-x*x/2));
        }
        return 0;
    }

    public static double relu(double x)
    {
        if ( x <= 0 ) return 0;
        return x;
    }

    /**
     * Returns the erf, cumulative gaussian, at point x, with high speed and accuracy
     * @param x
     * @return
     */


    public static double getNormalDistribution(double x)
    {
        return Math.exp(-(x*x)/2)*p1;
    }

    public static double getStochasticControlLossFunction(double x)
    {
        return 0;
    }

    public static double sigmoid(double x)
    {
        return Sigmoid.getValue(x);
    }

    public static double getSigmoidDerivative(double x)
    {
        return Sigmoid.getDerivative(x);
    }

    public static double[] CartesianToSphericalCoordinates3D(double[] xyz)
    {
        double[] R_Theta_Phi = new double[3];
        R_Theta_Phi[0] = Math.sqrt(xyz[0]*xyz[0]+xyz[1]*xyz[1]+xyz[2]*xyz[2]);
        R_Theta_Phi[1] = Math.acos(xyz[2]/R_Theta_Phi[0]);
        R_Theta_Phi[2] = Math.atan2(xyz[1],xyz[0]);
        return R_Theta_Phi;
    }

    public static double[] sphericalToCartesianCoordinates3D(double[] R_Theta_Phi)
    {
        double[] result = new double[3];
        double sinTheta = Math.sin(R_Theta_Phi[1]);
        result[0] = R_Theta_Phi[0]*sinTheta*Math.cos(R_Theta_Phi[2]);
        result[1] = R_Theta_Phi[0]*sinTheta*Math.sin(R_Theta_Phi[2]);
        result[2] = R_Theta_Phi[0]*Math.cos(R_Theta_Phi[1]);
        return result;
    }
}