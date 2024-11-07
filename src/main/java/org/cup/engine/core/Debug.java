package org.cup.engine.core;

/**
 * Provides utility methods for logging messages with different severity levels,
 * including informational, warning, and error messages.
 * The `Debug` class uses ANSI color codes to format messages in the console,
 * making it easier to differentiate between log types.
 */
public class Debug {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";

    /**
     * Logs an informational message prefixed with "[INFO]".
     *
     * @param text The message to log.
     */
    public static void log(Object text) {
        System.out.println("[INFO]\t\t" + text.toString());
    }

    /**
     * Logs a message prefixed with "[ENGINE]", intended for messages specific
     * to the game engine's internal operations.
     *
     * @param text The message to log.
     */
    public static void engineLog(Object text) {
        System.out.println("[ENGINE]\t" + text.toString());
    }

    /**
     * Logs an error message in red text, prefixed with "[ENGINE]".
     * This method is used to highlight critical errors within the engine.
     *
     * @param text The error message to log.
     */
    public static void engineLogErr(Object text) {
        System.out.println(ANSI_RED + "[ENGINE]\t" + text.toString() + ANSI_RESET);
    }

    /**
     * Logs a warning message in yellow text, prefixed with "[WARN]".
     * This method is intended for non-critical warnings that require attention.
     *
     * @param text The warning message to log.
     */
    public static void warn(Object text) {
        System.out.println(ANSI_YELLOW + "[WARN]\t" + text.toString() + ANSI_RESET);
    }
    
    /**
     * Logs an error message in red text, prefixed with "[ERROR]".
     * This method highlights general application errors that need to be addressed.
     *
     * @param text The error message to log.
     */
    public static void err(Object text) {
        System.out.println(ANSI_RED + "[ERROR]\t" + text.toString() + ANSI_RESET);
    }

    /**
     * Logs an informational message in cyan text, prefixed with "[INFO]".
     * This method provides visually distinct informational output in the console.
     *
     * @param text The informational message to log.
     */
    public static void info(Object text) {
        System.out.println(ANSI_CYAN + "[INFO]\t" + text.toString() + ANSI_RESET);
    }
}
