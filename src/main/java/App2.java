import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

public class App2 {
    public static void main(String[] args) {
        run(System.in, System.out);
    }

    public static void run(InputStream input, PrintStream console) {
        try (Scanner scanner = new Scanner(input)) {
            console.print("P: ");
            double p = Double.parseDouble(scanner.nextLine().trim());

            console.print("DOF: ");
            int degreesOfFreedom = Integer.parseInt(scanner.nextLine().trim());

            double x = new Logic().findX(p, degreesOfFreedom);
            console.printf(Locale.US, "%nX = %.5f%n", x);
        }
    }
}