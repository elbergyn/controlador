package net.inforgyn.util;

public class LoggerUtil {
    private static LoggerUtil instance;

    public LoggerUtil() {
    }

    public static LoggerUtil getInstance() {
        if(instance == null){
            instance = new LoggerUtil();
        }
        return instance;
    }

    
}