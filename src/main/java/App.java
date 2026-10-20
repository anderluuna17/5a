import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

public class App {
    public static void main(String[] args) { run(System.in, System.out); }

    public static void run(InputStream input, PrintStream console) {
        try (Scanner scanner = new Scanner(input)) {
            console.print("Valor inicial: ");
            double initialValue = Double.parseDouble(scanner.nextLine().trim());
            console.print("Valor final: ");
            double finalValue = Double.parseDouble(scanner.nextLine().trim());
            console.print("DOF: ");
            int degreesOfFreedom = Integer.parseInt(scanner.nextLine().trim());
            double result = new Logic().logic1a0(initialValue, finalValue, degreesOfFreedom);
            console.printf(Locale.US, "%n%.5f%n", result);
        } catch (RuntimeException | java.io.IOException exception) {
            console.println("Datos inválidos: " + exception.getMessage());
        }
    }
}
