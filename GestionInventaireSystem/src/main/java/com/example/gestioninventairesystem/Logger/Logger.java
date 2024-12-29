package com.example.gestioninventairesystem.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static BufferedWriter writer;
    private static final String Path = "src/main/logs/";

    static {
        try {
            FileWriter fileWriter = new FileWriter(logPattern(), true);
            writer = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            throw new Error("An error occurred while initializing the log file." + "\n" + e);
        }
    }

    public Logger() {}

    private static String logPattern() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return Path + currentDate.format(formatter) + ".log";
    }

    private static String logDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }

    private static String logger() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 2; i < stackTrace.length; i++) {
            StackTraceElement element = stackTrace[i];
            String className = element.getClassName();
            if (!className.startsWith("java.lang.")) {
                return className;
            }
        }
        return "Unknown";
    }

    private static void logger(String level, String message) {
        try {
            message = logDate() +
                    " [" + Thread.currentThread() + "]" +
                    " [" + level + "]" +
                    " [" + logger() + "]" +
                    " " + message;
            writer.write(message);
            writer.newLine();
            Console(level, message);
            writer.flush();
        } catch (IOException e) {
            throw new Error("An error occurred while writing to the log file." + "\n" + e);
        }
    }

    private static void Console(String level, String message) {
        if(level.equals("INFO") || level.equals("WARNING")) System.out.println(message);
        if(level.equals("ERROR")) System.err.println(message);
    }

    public static void info(String message) {
        logger("INFO", message);
    }

    public static void error(String message) {
        logger("ERROR", message);
    }

    public static void warning(String message) {
        logger("WARNING", message);
    }

    public static void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            throw new Error("An error occurred while closing the log file writer." + "\n" + e);
        }
    }
}
