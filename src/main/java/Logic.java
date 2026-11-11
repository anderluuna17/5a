import java.io.IOException;
import java.util.Locale;

public class Logic {
    private final SimpsonIntegration integration;
    private final Output output;

    public Logic() { this(new SimpsonIntegration(), new Output()); }
    public Logic(SimpsonIntegration integration, Output output) { this.integration = integration; this.output = output; }

    public double logic1a0(double initialValue, double finalValue, int degreesOfFreedom) throws IOException {
        double result = integration.integrate(initialValue, finalValue, degreesOfFreedom);
        output.writeData("output/output5.txt", String.format(Locale.US, "%.5f", result));
        return result;
    }


    public double findX(double p, int degreesOfFreedom) {
    if (!Double.isFinite(p) || p < 0 || p >= 0.5) {
        throw new IllegalArgumentException("P debe estar entre 0 y 0.5.");
    }

    double low = 0;
    double high = 1;

    while (integration.integrate(0, high, degreesOfFreedom) < p) {
        high *= 2;
    }

    while (high - low > 0.00001) {
        double mid = (low + high) / 2;
        double area = integration.integrate(0, mid, degreesOfFreedom);

        if (area < p) {
            low = mid;
        } else {
            high = mid;
        }
    }

    return (low + high) / 2;
}
}
