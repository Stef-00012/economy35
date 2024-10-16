package org.cup.engine.core;

public class Debug {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static void log(String text) {
        System.out.println("[INFO]\t\t" + text);
    }

    public static void engineLog(String text) {
        System.out.println("[ENGINE]\t" + text);
    }

    public static void engineLogErr(String text) {
        System.out.println(ANSI_RED + "[ENGINE]\t" + text + ANSI_RESET);
    }

    public static void warn(String text) {
        System.out.println(ANSI_YELLOW + "[WARN]\t" + text + ANSI_RESET);
    }
}
