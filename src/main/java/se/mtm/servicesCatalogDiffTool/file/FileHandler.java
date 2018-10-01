package se.mtm.servicesCatalogDiffTool.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHandler {

    public Set<String> readFile(String path) {
        Set<String> mediaNumbers = new HashSet<>();

        Path p = Paths.get(path);
        try (Stream<String> stream = Files.lines(p)) {
            mediaNumbers = stream.collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return mediaNumbers;
    }

    public void writeOutputToFile(String path, Map<String, String> diffs) {
        String fileName = "diffs-" + LocalDate.now() + ".txt";
        File file = new File(path + File.separator + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (PrintWriter out = new PrintWriter(file)) {
            for (Map.Entry<String, String> entry : diffs.entrySet()) {
                if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                    out.print(entry.getKey() + " -->\n" + entry.getValue() + "\n");
                }
            }
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }
    }
}
