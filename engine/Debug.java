package engine;

public class Debug {
    public static void log(String text){
        System.out.println("[INFO]\t\t" + text);
    } 

    public static void engineLog(String text){
        System.out.println("[ENGINE]\t" + text);
    } 

    public static void warn(String text){
        System.out.println("[WARN]\t" + text);
    } 
}
