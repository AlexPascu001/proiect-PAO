package model.banking;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Scanner;
import java.util.function.Supplier;

public class TransactionFactory {
    private static int id = 1;

    public Transaction createTransaction(ResultSet in) {
        Supplier<Transaction> transactionSupplier = () -> new Transaction(id++, in);
        return transactionSupplier.get();
    }

    public Transaction createTransaction(Scanner in) {
        Supplier<Transaction> transactionSupplier = () -> {
            try {
                return new Transaction(id++, in);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        };
        return transactionSupplier.get();
    }
}
