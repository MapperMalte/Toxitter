package Avatar.Elemental.wind;

public class BookOfRass
{
    private static final double arcaneConstant = (1+Math.sqrt(1-2*Math.PI*Math.PI+6*Math.PI))/(2*Math.PI);
    private static final double p0 = Math.sqrt(Math.PI/2);
    private static final double p1 = 1/Math.sqrt(2*Math.PI);
    private static final double p3 = Math.sqrt(2/Math.PI);
    private static final double p4 = p0*p0;
    private static final double p5 = Math.sqrt(2);
    private static final double p7 = 2*Math.PI*arcaneConstant*arcaneConstant;

    public static double getErf(double x)
    {
        if ( x == 0 ) return 0;
        x = p5*x;
        double xSquared = x*x;
        double sdf = Math.exp(-xSquared/2);
        return 1-p3*(p0+(1/x)*(sdf-Math.sqrt(p4*xSquared+(sdf*Math.sqrt(1+p7*xSquared)/(1+arcaneConstant*xSquared)))));
    }

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
            return 1/8.*p1*Math.exp(-x*x/2)*(9*lambda*x+3*lambda*lambda*x*x+1/3*lambda*lambda*lambda*x*x*x+9);
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
        return 1/(1+Math.exp(-x));
    }

    public static double getSigmoidDerivative(double x)
    {
        return sigmoid(x)*(1-sigmoid(x));
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