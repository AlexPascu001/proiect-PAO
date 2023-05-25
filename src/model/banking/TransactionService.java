package model.banking;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private final Connection connection;
    private TransactionFactory transactionFactory = new TransactionFactory();
    private static TransactionService instance = null;

    private TransactionService(Connection connection) {
        this.connection = connection;
    }

    public static TransactionService getInstance(Connection connection) {
        if (instance == null) {
            instance = new TransactionService(connection);
        }
        return instance;
    }

    public void create(Transaction transaction) {
        try {
            String sql = "INSERT INTO Transactions (transactionID, fromIBAN, toIBAN, amount, description, date) VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, transaction.getTransactionID());
            preparedStatement.setString(2, transaction.getFromIBAN());
            preparedStatement.setString(3, transaction.getToIBAN());
            preparedStatement.setDouble(4, transaction.getAmount());
            preparedStatement.setString(5, transaction.getDescription());
            preparedStatement.setString(6, new SimpleDateFormat("yyyy-MM-dd").format(transaction.getDate()));
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public List<Transaction> read() {
        List<Transaction> transactions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Transactions";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet == null) {
                statement.close();
                return transactions;
            }
            while (resultSet.next()) {
                Transaction transaction = transactionFactory.createTransaction(resultSet);
                transaction.setTransactionID(resultSet.getInt("transactionID"));
                transactions.add(transaction);
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException e) {
            System.out.println("Error in TransactionService.read()");
            System.out.println(e.toString());
        }
        return transactions;
    }

    public Transaction read(int transactionID) {
        Transaction transaction = null;
        try {
            String sql = "SELECT * FROM Transactions WHERE transactionID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, transactionID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                transaction = transactionFactory.createTransaction(resultSet);
                transaction.setTransactionID(transactionID);
            }
            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
        return transaction;
    }

    public void update(Transaction transaction) {
        try {
            String sql = "UPDATE Transactions SET fromIBAN = ?, toIBAN = ?, amount = ?, description = ?, date = ? WHERE transactionID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, transaction.getFromIBAN());
            preparedStatement.setString(2, transaction.getToIBAN());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getDescription());
            preparedStatement.setString(5, new SimpleDateFormat("yyyy-MM-dd").format(transaction.getDate()));
            preparedStatement.setInt(6, transaction.getTransactionID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void delete(Transaction transaction) {
        try {
            String sql = "DELETE FROM Transactions WHERE transactionID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, transaction.getTransactionID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
