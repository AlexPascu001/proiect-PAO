package model.customer;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private final Connection connection;
    private final CustomerFactory customerFactory = new CustomerFactory();
    private static CustomerService instance = null;

    private CustomerService(Connection connection) {
        this.connection = connection;
    }

    public static CustomerService getInstance(Connection connection) {
        if (instance == null) {
            instance = new CustomerService(connection);
        }
        return instance;
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
            System.out.println(e);
        }
    }

    public List<Customer> read() {
        List<Customer> customers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Customers";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet == null) {
                statement.close();
                return customers;
            }
            while (resultSet.next()) {
                Customer customer = customerFactory.createCustomer(resultSet);
                customer.setCustomerID(resultSet.getInt("customerID"));
                customers.add(customer);
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException e) {
            System.out.println("Error in CustomerService.read()");
            System.out.println(e);
        }
        return customers;
    }

    public Customer read(int customerID) {
        Customer customer = null;
        try {
            String sql = "SELECT * FROM Customers WHERE customerID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                customer = customerFactory.createCustomer(resultSet);
                customer.setCustomerID(customerID);
            }
            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return customer;
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
            System.out.println(e);
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
            System.out.println(e);
        }
    }
}
