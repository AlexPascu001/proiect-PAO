package service;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class AuditService {

    //TODO: integrate audit service with the rest of the project
    private final String filePath;

    public AuditService(String filePath) {
        this.filePath = filePath;
    }

    public void logAction(String action) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String timestamp = now.format(dtf);
            fileWriter.write(timestamp + "," + action + ",");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void logObject(String objectCSV) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            fileWriter.write(objectCSV + ",");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void logException(Exception e) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            fileWriter.write(e.getMessage() + ",");
            fileWriter.close();
        } catch (IOException exception) {
            System.out.println("An error occurred.");
            exception.printStackTrace();
        }
    }

}
