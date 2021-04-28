package ind.payUMoney.automation.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceLoader {
    public static BufferedReader load(String pathWithFile) {
        ClassLoader classLoader = DataFactory.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(pathWithFile);
        if (inputStream == null) {
            throw new RuntimeException("Resource: " + pathWithFile + " not found.");
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
