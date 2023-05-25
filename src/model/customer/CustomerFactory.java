package model.customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.function.Supplier;

public class CustomerFactory {
    private static int id = 1;

    public Customer createCustomer(ResultSet in) {
//        return new Customer(id++, in);
        Supplier<Customer> customerSupplier = () -> new Customer(id++, in);
        return customerSupplier.get();
    }

    public Customer createCustomer(Scanner in) {
//        return new Customer(id++, in);
        Supplier<Customer> customerSupplier = () -> {
            try {
                return new Customer(id++, in);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
        return customerSupplier.get();
    }
}
