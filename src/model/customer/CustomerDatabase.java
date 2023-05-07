package model.customer;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerDatabase {
    private Connection connection;
    private CustomerFactory customerFactory;

    public CustomerDatabase(Connection connection) {
        this.connection = connection;
    }

    public void create(Customer customer) {
        try {
            String sql = "INSERT INTO Customers (customerID, firstName, lastName, CNP, birthDate, phone, email, street, city, country, zipCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customer.getCustomerID());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setString(4, customer.getCNP());
            preparedStatement.setString(5, new SimpleDateFormat("yyyy-MM-dd").format(customer.getBirthDate()));
            preparedStatement.setString(6, customer.getPhoneNumber());
            preparedStatement.setString(7, customer.getEmail());
            preparedStatement.setString(8, customer.getAddress().getStreet());
            preparedStatement.setString(9, customer.getAddress().getCity());
            preparedStatement.setString(10, customer.getAddress().getCountry());
            preparedStatement.setInt(11, customer.getAddress().getZipCode());
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public List<Customer> read() {
        List<Customer> customers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Customers";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Customer customer = customerFactory.createCustomer(resultSet);
                customers.add(customer);
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
        return customers;
    }

    public void update(Customer customer) {
        try {
            String sql = "UPDATE Customers SET firstName = ?, lastName = ?, CNP = ?, birthDate = ?, phone = ?, email = ?, street = ?, city = ?, country = ?, zipCode = ? WHERE customerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getCNP());
            preparedStatement.setString(4, new SimpleDateFormat("yyyy-MM-dd").format(customer.getBirthDate()));
            preparedStatement.setString(5, customer.getPhoneNumber());
            preparedStatement.setString(6, customer.getEmail());
            preparedStatement.setString(7, customer.getAddress().getStreet());
            preparedStatement.setString(8, customer.getAddress().getCity());
            preparedStatement.setString(9, customer.getAddress().getCountry());
            preparedStatement.setInt(10, customer.getAddress().getZipCode());
            preparedStatement.setInt(11, customer.getCustomerID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void delete(Customer customer) {
        try {
            String sql = "DELETE FROM Customers WHERE customerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customer.getCustomerID());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
