package service;

import java.io.File;
import java.io.FileWriter;

public class AuditService {
    private static AuditService singleton;
    public static AuditService getInstance() {
        if (singleton == null) {
            singleton = new AuditService();
        }
        return singleton;
    }
    private FileWriter writer;
    private AuditService() {
        final String auditFileName = "audit.txt";
        File file = new File(auditFileName);
        boolean exists = file.exists();
        try {
            writer = new FileWriter(file, true);
        } catch (Exception e) {
            writer = null;
            write("Failed to open audit log " + auditFileName + ": " + e.getMessage());
            return;
        }
        if (!exists) {
            write("actionName,actionUnixTimestamp\n");
        }
    }
    private void write(String s) {
        try {
            writer.write(s);
            writer.flush();
            return;
        } catch (Exception e) {
            System.err.println("Failed to write audit log: " + e.getMessage());
        }
        System.err.println("AUDIT LOG: " + s);
    }
    private String csvEscape(String s) {
        final String badChars = "\",\n";
        if (!s.contains(badChars)) {
            return s;
        }
        return "\"" + s.replace("\"", "\"\"") + "\"";
    }
    private void log(String actionName, long actionUnixTimestamp) {
        write(csvEscape(actionName) + "," + csvEscape(Long.toString(actionUnixTimestamp)) + "\n");
    }
    public void log(String actionName) {
        log(actionName, System.currentTimeMillis() / 1000);
    }
}
