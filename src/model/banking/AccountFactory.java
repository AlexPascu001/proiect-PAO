package model.banking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

public class AccountFactory {
    private static int id = 0;

    public Account createAccount(ResultSet in) throws SQLException {
        Supplier<Account> accountSupplier = () -> new Account(id++, in);
        return accountSupplier.get();
    }
}
