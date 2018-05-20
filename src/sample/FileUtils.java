package sample;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

    public static void writeToFile(String fileName, String content) {

        try {
            Files.write(Paths.get(fileName), content.getBytes());
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Cannot write content to file %s", fileName));
        }
    }

}
