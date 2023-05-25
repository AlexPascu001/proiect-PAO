package model.banking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private final Connection connection;
    private AccountFactory accountFactory = new AccountFactory();
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
            String sql = "INSERT INTO accounts (accountID, IBAN, swiftCode, bankName, name, balance, customerID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account.getAccountID());
            preparedStatement.setString(2, account.getIBAN());
            preparedStatement.setString(3, account.getSwiftCode());
            preparedStatement.setString(4, account.getBankName());
            preparedStatement.setString(5, account.getName());
            preparedStatement.setDouble(6, account.getBalance());
            preparedStatement.setInt(7, account.getCustomerID());
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
            if (resultSet == null) {
                statement.close();
                return accounts;
            }
            while (resultSet.next()) {
                Account account = accountFactory.createAccount(resultSet);
                account.setAccountID(resultSet.getInt("accountID"));
                accounts.add(account);
            }
            resultSet.close();
            statement.close();
        }
        catch (Exception e) {
            System.out.println("Error in AccountService.read()");
            System.out.println(e.toString());
        }
        return accounts;
    }

    public Account read(int accountID) {
        Account account = null;
        try {
            String sql = "SELECT * FROM Accounts WHERE accountID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                account = accountFactory.createAccount(resultSet);
                account.setAccountID(accountID);
            }
            resultSet.close();
            preparedStatement.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        return account;
    }

    public List<Account> readCustomer(int customerID) {
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Accounts WHERE customerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = accountFactory.createAccount(resultSet);
                accounts.add(account);
            }
            resultSet.close();
            preparedStatement.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        return accounts;
    }

    public void update(Account account) {
        try {
            String sql = "UPDATE Accounts SET IBAN = ?, swiftCode = ?, bankName = ?, name = ?, balance = ?, customerID = ? WHERE accountID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getIBAN());
            preparedStatement.setString(2, account.getSwiftCode());
            preparedStatement.setString(3, account.getBankName());
            preparedStatement.setString(4, account.getName());
            preparedStatement.setDouble(5, account.getBalance());
            preparedStatement.setInt(6, account.getCustomerID());
            preparedStatement.setInt(7, account.getAccountID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void delete(Account account) {
        try {
            String sql = "DELETE FROM Accounts WHERE accountID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, account.getAccountID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
