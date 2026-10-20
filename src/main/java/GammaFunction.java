public class GammaFunction {
    private static final double[] COEFFICIENTS = {676.5203681218851, -1259.1392167224028, 771.3234287776531, -176.6150291621406, 12.507343278686905, -0.13857109526572012, 9.984369578019572E-6, 1.5056327351493116E-7};

    public double computeIntGamma(int intValue) {
        if (intValue <= 0) throw new IllegalArgumentException("El argumento de gamma debe ser positivo.");
        return computeDblGamma(intValue);
    }

    public double computeDblGamma(double doubleValue) {
        if (!Double.isFinite(doubleValue) || doubleValue <= 0) throw new IllegalArgumentException("El argumento de gamma debe ser positivo y finito.");
        if (doubleValue < 0.5) return Math.PI / (Math.sin(Math.PI * doubleValue) * computeDblGamma(1 - doubleValue));
        double z = doubleValue - 1;
        double sum = 0.99999999999980993;
        for (int i = 0; i < COEFFICIENTS.length; i++) sum += COEFFICIENTS[i] / (z + i + 1);
        double t = z + COEFFICIENTS.length - 0.5;
        return Math.sqrt(2 * Math.PI) * Math.pow(t, z + 0.5) * Math.exp(-t) * sum;
    }
}
