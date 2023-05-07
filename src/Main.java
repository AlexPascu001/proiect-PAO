import service.*;
import model.banking.*;
import model.customer.*;
import model.card.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/ProiectPAO";
        String username = "alex";
        String password = "alex";


        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        }
        catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to database!", e);
        }
    }
}