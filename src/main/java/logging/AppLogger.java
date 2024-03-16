package logging;

import org.apache.logging.log4j.LogManager;

public class AppLogger {

    private AppLogger() {
        throw new IllegalStateException("Utility class");
    }

    public static void logError(Class<?> className, String message) {
        LogManager.getLogger(className).error(message);
    }

    public static void logWarn(Class<?> className, String message) {
        LogManager.getLogger(className).warn(message);
    }

    public static void logInfo(Class<?> className, String message) {
        LogManager.getLogger(className).info(message);
    }
}
