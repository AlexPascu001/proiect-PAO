package model.customer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerFactory {
    private static int id = 0;

    public Customer createCustomer(ResultSet in) throws SQLException {
        return new Customer(id++, in);
    }
}
