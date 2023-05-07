package service;
import model.banking.*;
import model.customer.*;
import model.card.*;

import java.util.Date;

public class BankService {
    private static int customerIDs = 0;
    private static int cardIDs = 0;
    AuditService auditService;

    public Account createAccount(String IBAN, String swiftCode, String bankName, String name, double balance, int customerID) {
        return new Account(IBAN, swiftCode, bankName, name, balance, customerID);
    }

    public Customer createCustomer(String firstName, String lastName, String CNP, Address address, String phoneNumber, String email, Date birthDate) throws Exception {
        try {
            return new Customer(++customerIDs, firstName, lastName, CNP, address, phoneNumber, email, birthDate);
        } catch (Exception e) {
            auditService.logException(e);
        }
        return null;
    }

    public Card createCard(String IBAN) {
        return new Card(++cardIDs, IBAN) {
            @Override
            public double fee() {
                return 0;
            }
        };
    }

    public MasterCard createMasterCard(String IBAN) {
        return new MasterCard(++cardIDs, IBAN);
    }

    public Visa createVisa(String IBAN) {
        return new Visa(++cardIDs, IBAN);
    }

    public SavingsAccount createSavingsAccount(String IBAN, String swiftCode, String bankName, String name, double balance, int customerID, double interestRate, Date startDate) {
        return new SavingsAccount(IBAN, swiftCode, bankName, name, balance, customerID, interestRate, startDate);
    }

    public Transaction createTransaction(String fromIBAN, String toIBAN, double amount, String description, Date date) throws Exception {
        try {
            return new Transaction(fromIBAN, toIBAN, amount, description, date);
        }
        catch (Exception e) {
            auditService.logException(e);
        }
        return null;
    }

}
