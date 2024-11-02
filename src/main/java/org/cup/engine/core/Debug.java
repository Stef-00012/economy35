package org.cup.engine.core;

public class Debug {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void log(Object text) {
        System.out.println("[INFO]\t\t" + text.toString());
    }

    public static void engineLog(Object text) {
        System.out.println("[ENGINE]\t" + text.toString());
    }

    public static void engineLogErr(Object text) {
        System.out.println(ANSI_RED + "[ENGINE]\t" + text.toString() + ANSI_RESET);
    }

    public static void warn(Object text) {
        System.out.println(ANSI_YELLOW + "[WARN]\t" + text.toString() + ANSI_RESET);
    }
    
    public static void err(Object text) {
        System.out.println(ANSI_RED + "[ERROR]\t" + text.toString() + ANSI_RESET);
    }

    public static void info(Object text) {
        System.out.println(ANSI_CYAN + "[INFO]\t" + text.toString() + ANSI_RESET);
    }
}
