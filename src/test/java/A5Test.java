import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class A5Test {
    private final Path outputPath = Path.of("output", "output5.txt");

    @AfterEach
    void cleanOutput() throws IOException {
        Files.deleteIfExists(outputPath);
    }

    @Test
    void pdfReferenceResults() throws IOException {
        Logic logic = new Logic();
        assertEquals(0.35006, logic.logic1a0(0, 1.1, 9), 0.000005);
        assertEquals(0.36757, logic.logic1a0(0, 1.1812, 10), 0.000005);
        assertEquals(0.49590, logic.logic1a0(0, 2.750, 30), 0.000005);
    }

    @Test
    void gammaSupportsIntegerAndDecimalArguments() {
        GammaFunction gamma = new GammaFunction();
        assertEquals(24, gamma.computeIntGamma(5), 1e-10);
        assertEquals(24, gamma.computeDblGamma(5), 1e-10);
        assertEquals(Math.sqrt(Math.PI), gamma.computeDblGamma(0.5), 1e-10);
        assertEquals(1.329340388, gamma.computeDblGamma(2.5), 1e-9);
        assertEquals(3.625609908, gamma.computeDblGamma(0.25), 1e-9);
        assertThrows(IllegalArgumentException.class, () -> gamma.computeIntGamma(0));
        assertThrows(IllegalArgumentException.class, () -> gamma.computeDblGamma(-1));
        assertThrows(IllegalArgumentException.class, () -> gamma.computeDblGamma(Double.NaN));
    }

    @Test
    void integrationHandlesOffsetAndReversedIntervals() {
        SimpsonIntegration integration = new SimpsonIntegration();
        assertEquals(0.23169312, integration.integrate(0.5, 1.5, 10), 0.00000001);
        assertEquals(-integration.integrate(0.5, 1.5, 10), integration.integrate(1.5, 0.5, 10), 1e-12);
        assertEquals(0, integration.integrate(1, 1, 5));
        assertEquals(10, integration.getLastSegments());
        assertTrue(integration.integrate(0, 100, 2) > 0);
    }

    @Test
    void invalidIntegrationDataIsRejected() {
        SimpsonIntegration integration = new SimpsonIntegration();
        assertThrows(IllegalArgumentException.class, () -> integration.integrate(Double.NaN, 1, 3));
        assertThrows(IllegalArgumentException.class, () -> integration.integrate(0, Double.POSITIVE_INFINITY, 3));
        assertThrows(IllegalArgumentException.class, () -> integration.integrate(0, 1, 0));
    }

    @Test
    void outputContainsOnlyFiveDecimalPlaces() throws IOException {
        new Output().writeData(outputPath.toString(), String.format(Locale.US, "%.5f", 0.35006));
        assertEquals("0.35006", Files.readString(outputPath));
        new Output().writeData("output5-test.txt", "1.00000");
        assertEquals("1.00000", Files.readString(Path.of("output5-test.txt")));
        Files.deleteIfExists(Path.of("output5-test.txt"));
    }

    @Test
    void appReadsThreeInputsDisplaysResultAndWritesFile() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        App.run(new ByteArrayInputStream("0\n1.1\n9\n".getBytes()), new PrintStream(bytes));
        String console = bytes.toString();
        assertTrue(console.contains("Valor inicial:"));
        assertTrue(console.contains("Valor final:"));
        assertTrue(console.contains("DOF:"));
        assertTrue(console.contains("0.35006"));
        assertEquals("0.35006", Files.readString(outputPath));
    }

    @Test
    void appMainUsesStandardInputAndOutput() throws IOException {
        new App();
        var originalIn = System.in;
        var originalOut = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream("0\n1.1\n9\n".getBytes()));
            System.setOut(new PrintStream(bytes));
            App.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
        assertTrue(bytes.toString().contains("0.35006"));
    }

    @Test
    void appReportsInvalidInput() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        App.run(new ByteArrayInputStream("abc\n".getBytes()), new PrintStream(bytes));
        assertTrue(bytes.toString().contains("Datos inválidos:"));
        bytes.reset();
        App.run(new ByteArrayInputStream("0\n1\n-2\n".getBytes()), new PrintStream(bytes));
        assertTrue(bytes.toString().contains("Datos inválidos:"));
        App.run(new ByteArrayInputStream(new byte[0]), new PrintStream(bytes));
        assertTrue(bytes.toString().contains("Datos inválidos:"));
    }
}
