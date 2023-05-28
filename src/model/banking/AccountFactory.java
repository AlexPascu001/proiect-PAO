package model.banking;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Scanner;
import java.util.function.Supplier;

public class AccountFactory {
    private static int id = 1;

    public Account createAccount(ResultSet in) {
        Supplier<Account> accountSupplier = () -> new Account(id++, in);
        return accountSupplier.get();
    }

    public Account createAccount(Scanner in) {
        Supplier<Account> accountSupplier = () -> {
            try {
                return new Account(id++, in);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        };
        return accountSupplier.get();
    }
}
