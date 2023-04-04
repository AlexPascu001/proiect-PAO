package Service;
import Banking.*;
import Customer.*;
import Card.*;

import java.util.Date;

public class BankService {
    private static int customerIDs = 0;
    private static int cardIDs = 0;

    //TODO: add methods for creating accounts, customers, cards, etc.
    public Account createAccount(String IBAN, String swiftCode, String bankName, String name, double balance, int customerID) {
        return new Account(IBAN, swiftCode, bankName, name, balance, customerID);
    }

    public Customer createCustomer(String firstName, String lastName, String CNP, Address address, String phoneNumber, String email, Date birthDate) throws Exception {
        try {
            return new Customer(++customerIDs, firstName, lastName, CNP, address, phoneNumber, email, birthDate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Card createCard(String IBAN) {
        return new Card(++cardIDs, IBAN);
    }

    public SavingsAccount createSavingsAccount(String IBAN, String swiftCode, String bankName, String name, double balance, int customerID, double interestRate, Date startDate) {
        return new SavingsAccount(IBAN, swiftCode, bankName, name, balance, customerID, interestRate, startDate);
    }

    public Transaction createTransaction(String fromIBAN, String toIBAN, double amount, String description, Date date) throws Exception {
        try {
            return new Transaction(fromIBAN, toIBAN, amount, description, date);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
