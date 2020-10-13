package Avatar.Elemental.wind.math.functions;

import Avatar.Elemental.wind.math.Function;

public class GammaFunction implements Function
{
    /**
     * Calculate the faculty
     * A generated approximation of the gamma function related to Windschitl’s formula
     * @param n
     * @return
     */
    private double faculty_DaweiLuLixinSongCongxuMa(double n)
    {
        return Math.sqrt(2*Math.PI*n)*Math.pow(n/Math.E,n)*Math.pow(n*Math.sinh(1/n+1/(810*n*n*n*n*n*n*n)),n/2);
    }
    /**
     * https://www.researchgate.net/profile/Cristinel_Mortici/publication/265333750_Very_accurate_estimates_of_the_polygamma_functions/links/54887bdf0cf289302e30b0b8/Very-accurate-estimates-of-the-polygamma-functions.pdf
     * Absolute error smaller than -1/720(n+3)!(x^(n+4))
     * @return
     */
    private double getCristinelMorticiNthDigamma(double x, double n)
    {
        double nMinusOneFaculty = faculty_DaweiLuLixinSongCongxuMa(n-1);
        double pow = Math.pow(x,n);
        return nMinusOneFaculty/pow
                + 0.5*nMinusOneFaculty*n/(pow*x)
                + 1./12*nMinusOneFaculty*n*(n+1)/(pow*x*x);
    }

    /**
     * Error smaller than 1/(30*x^5)
     * @param x
     * @return
     */
    private double digamma_getCristinelMortici(double x)
    {
        return 1/x+1/(2*x*x)+1/(6*x*x*x);
    }
    /**
     * "A more accurate approximation for the gamma function"
     * https://reader.elsevier.com/reader/sd/pii/S0022314X16000068?token=9C9F21F61239DB4108E85375D7DE7001BAE466C519EC472123A1E4850A9D7420EBEB4C44765524B2F9E8085303C098C5
     * @return gamma(x+1)
     */
    private double ChaoPingChen(double x)
    {
        return Math.sqrt(2*Math.PI*x)*Math.pow(x/Math.E,x)*Math.pow((1+1./(12*x*x*x+24./7.*x-0.2)),x*x+53./210.);
    }
    @Override
    public double getValue(double x)
    {
        return ChaoPingChen(x-1);
    }

    @Override
    public double getDerivative(double x)
    {
        return digamma_getCristinelMortici(x)*getValue(x);
    }
}
