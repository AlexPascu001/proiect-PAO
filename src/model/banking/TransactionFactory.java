package model.banking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

public class TransactionFactory {
    private static int id = 0;

    public Transaction createTransaction(ResultSet in) throws SQLException {
        Supplier<Transaction> transactionSupplier = () -> new Transaction(id++, in);
        return transactionSupplier.get();
    }
}
