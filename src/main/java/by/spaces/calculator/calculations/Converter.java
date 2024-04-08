package by.spaces.calculator.calculations;

import java.io.*;

public class Converter {
    static {
        try {
            System.load(extractLibrary("Converter"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String extractLibrary(String name) throws IOException {
        File tempDir = new File(System.getProperty("java.io.tmpdir"));
        File tempLib = File.createTempFile(name, ".dll", tempDir);
        try (InputStream in = Converter.class.getResourceAsStream("/native/" + System.mapLibraryName(name));
             OutputStream out = new FileOutputStream(tempLib)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        return tempLib.getAbsolutePath();
    }


    public native String convertBase(String number, int sourceBase, int targetBase);
    public native boolean validateNumber(String number, int base);
}
