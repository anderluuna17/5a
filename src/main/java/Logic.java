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
}
