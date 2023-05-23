package service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AuditService {
    private static final String LOG_FILE_PATH = "audit.csv";
    public static void logAction(String methodName, String actionName, String message) {
        String logEntry = String.format("%s,%s,%s,%s%n", LocalDateTime.now(), methodName, actionName, message);
        try (FileWriter fileWriter = new FileWriter(LOG_FILE_PATH, true)) {
            fileWriter.write(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
