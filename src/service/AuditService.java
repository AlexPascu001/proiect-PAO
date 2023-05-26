package service;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class AuditService {

    private final String filePath;

    public AuditService(String filePath) {
        this.filePath = filePath;
    }

    public void logAction(String action) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            if (action.trim().length() == 0) {
                return;
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String timestamp = now.format(dtf);
            fileWriter.write(action + ", " + timestamp + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
