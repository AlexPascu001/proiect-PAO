package model.banking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private Connection connection;
    private AccountFactory accountFactory;
    private static AccountService instance = null;

    private AccountService(Connection connection) {
        this.connection = connection;
    }

    public static AccountService getInstance(Connection connection) {
        if (instance == null) {
            instance = new AccountService(connection);
        }
        return instance;
    }
    
    public void create(Account account) {
        try {
            String sql = "INSERT INTO account (IBAN, swiftCode, bankName, name, balance, customerID) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getIBAN());
            preparedStatement.setString(2, account.getSwiftCode());
            preparedStatement.setString(3, account.getBankName());
            preparedStatement.setString(4, account.getName());
            preparedStatement.setDouble(5, account.getBalance());
            preparedStatement.setInt(6, account.getCustomerID());
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public List<Account> read() {
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Accounts";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Account account = accountFactory.createAccount(resultSet);
                accounts.add(account);
            }
            resultSet.close();
            statement.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        return accounts;
    }

    public void update(Account account) {
        try {
            String sql = "UPDATE Accounts SET IBAN = ?, swiftCode = ?, bankName = ?, name = ?, balance = ?, customerID = ? WHERE IBAN = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getIBAN());
            preparedStatement.setString(2, account.getSwiftCode());
            preparedStatement.setString(3, account.getBankName());
            preparedStatement.setString(4, account.getName());
            preparedStatement.setDouble(5, account.getBalance());
            preparedStatement.setInt(6, account.getCustomerID());
            preparedStatement.setString(7, account.getIBAN());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void delete(Account account) {
        try {
            String sql = "DELETE FROM Accounts WHERE customerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account.getCustomerID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
