package model.customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

public class CustomerFactory {
    private static int id = 0;

    public Customer createCustomer(ResultSet in) throws SQLException {
//        return new Customer(id++, in);
        Supplier<Customer> customerSupplier = () -> new Customer(id++, in);
        return customerSupplier.get();
    }
}
