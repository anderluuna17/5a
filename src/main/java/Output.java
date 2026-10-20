import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Output {
    public void writeData(String outFile, String outText) throws IOException {
        Path path = Path.of(outFile);
        Path parent = path.getParent();
        if (parent != null) Files.createDirectories(parent);
        Files.writeString(path, outText, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }
}
