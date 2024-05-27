package by.spaces.calculator.calculations;

import by.spaces.calculator.calculations.cpp.ConverterC;

import java.io.*;

public class ExtractLibClass {
    public static String extractLibrary(String name) throws IOException {
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File tempLib = File.createTempFile(name, ".dll", tempDir);
        try (InputStream in = ConverterC.class.getResourceAsStream("/native/" + System.mapLibraryName(name));
             OutputStream out = new FileOutputStream(tempLib)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        return tempLib.getAbsolutePath();
    }

    private ExtractLibClass(){}
}
