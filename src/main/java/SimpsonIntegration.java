public class SimpsonIntegration {
    private final GammaFunction gammaFunction;
    private int lastSegments;

    public SimpsonIntegration() { this(new GammaFunction()); }
    public SimpsonIntegration(GammaFunction gammaFunction) { this.gammaFunction = gammaFunction; }

    public double integrate(double initialValue, double finalValue, int degreesOfFreedom) {
        if (!Double.isFinite(initialValue) || !Double.isFinite(finalValue)) throw new IllegalArgumentException("Los límites deben ser finitos.");
        if (degreesOfFreedom <= 0) throw new IllegalArgumentException("Los grados de libertad deben ser positivos.");
        if (initialValue == finalValue) { lastSegments = 10; return 0; }
        int segments = 10;
        double previous = simpson(initialValue, finalValue, degreesOfFreedom, segments);
        while (true) {
            segments *= 2;
            double current = simpson(initialValue, finalValue, degreesOfFreedom, segments);
            if (Math.abs(current - previous) < 0.00001) { lastSegments = segments; return current; }
            previous = current;
        }
    }

    public int getLastSegments() { return lastSegments; }

    private double simpson(double start, double end, int dof, int segments) {
        double width = (end - start) / segments;
        double sum = density(start, dof) + density(end, dof);
        for (int i = 1; i < segments; i++) sum += (i % 2 == 0 ? 2 : 4) * density(start + i * width, dof);
        return sum * width / 3;
    }

    private double density(double x, int dof) {
        double coefficient = gammaFunction.computeDblGamma((dof + 1.0) / 2) / (Math.sqrt(dof * Math.PI) * gammaFunction.computeDblGamma(dof / 2.0));
        return coefficient * Math.pow(1 + x * x / dof, -(dof + 1.0) / 2);
    }
}
