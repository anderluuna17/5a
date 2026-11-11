import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class A6Test {
    @Test
    void findXInvertsAreaForGivenProbabilityAndDegreesOfFreedom() {
        assertEquals(1.1812, new Logic().findX(0.36757, 10), 0.0001);
    }

    @Test
    void findXHandlesZeroProbabilityAndRejectsInvalidInputs() {
        Logic logic = new Logic();
        assertEquals(0, logic.findX(0, 10), 0.00001);
        assertThrows(IllegalArgumentException.class, () -> logic.findX(Double.NaN, 10));
        assertThrows(IllegalArgumentException.class, () -> logic.findX(-0.1, 10));
        assertThrows(IllegalArgumentException.class, () -> logic.findX(0.5, 10));
        assertThrows(IllegalArgumentException.class, () -> logic.findX(0.2, 0));
    }

    @Test
    void app2ReadsProbabilityAndDegreesOfFreedomAndPrintsX() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        App2.run(new ByteArrayInputStream("0.36757\n10\n".getBytes()), new PrintStream(bytes));

        String console = bytes.toString();
        assertTrue(console.contains("P:"));
        assertTrue(console.contains("DOF:"));
        assertTrue(console.contains("X = 1.18118"));
    }

    @Test
    void app2MainUsesStandardInputAndOutput() {
        new App2();
        var originalIn = System.in;
        var originalOut = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream("0.36757\n10\n".getBytes()));
            System.setOut(new PrintStream(bytes));
            App2.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        assertTrue(bytes.toString().contains("X = 1.18118"));
    }
}
