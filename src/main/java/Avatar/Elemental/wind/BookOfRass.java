package Avatar.Elemental.wind;

public class BookOfRass
{
    public static double relu(double x)
    {
        if ( x <= 0 ) return 0;
        return x;
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