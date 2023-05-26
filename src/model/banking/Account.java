package model.banking;

import model.card.*;
import exceptions.InsufficientFundsException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class Account implements Comparator<Transaction> {
    protected int accountID;
    protected String IBAN;
    protected String swiftCode;
    protected String bankName;
    protected String name;
    protected double balance;
    protected int customerID;
    protected Card card;


    public Account(int accountID, String IBAN, String swiftCode, String bankName, String name, double balance, int customerID, Card card) {
        this.accountID = accountID;
        this.IBAN = IBAN;
        this.swiftCode = swiftCode;
        this.bankName = bankName;
        this.name = name;
        this.balance = balance;
        this.customerID = customerID;
        this.card = card;
    }

    public Account(int accountID, String IBAN, String swiftCode, String bankName, String name, double balance, int customerID) {
        this.accountID = accountID;
        this.IBAN = IBAN;
        this.swiftCode = swiftCode;
        this.bankName = bankName;
        this.name = name;
        this.balance = balance;
        this.customerID = customerID;
    }

    public Account(int accountID, String name, int customerID, int uniqueID) {
        this.accountID = accountID;
        this.name = name;
        this.customerID = customerID;
        this.IBAN = generateIBAN(uniqueID);
        this.swiftCode = generateSwiftCode(uniqueID);
    }

    public Account(int accountID, ResultSet in) {
        this.accountID = accountID;
        this.read(in);
    }

    public Account(int accountID, Scanner in) throws ParseException {
        try {
            this.accountID = accountID;
            this.read(in);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void read(ResultSet in) {
        try {
            this.accountID = in.getInt("accountID");
            this.IBAN = in.getString("IBAN");
            this.swiftCode = in.getString("swiftCode");
            this.bankName = in.getString("bankName");
            this.name = in.getString("name");
            this.balance = in.getDouble("balance");
            this.customerID = in.getInt("customerID");
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    private void read(Scanner in) {
        System.out.println("IBAN: ");
        this.IBAN = in.nextLine();
        System.out.println("Swift code: ");
        this.swiftCode = in.nextLine();
        System.out.println("Bank name: ");
        this.bankName = in.nextLine();
        System.out.println("Name: ");
        this.name = in.nextLine();
        System.out.println("Balance: ");
        this.balance = in.nextDouble();
        in.nextLine();
        System.out.println("Customer ID: ");
        this.customerID = in.nextInt();
        in.nextLine();
    }

    public int compare(Transaction t1, Transaction t2) {
        return t1.getDate().compareTo(t2.getDate());
    }

   public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getCustomerID() {
        return customerID;
    }

    private String generateIBAN(int uniqueID) {
        return "RO" + uniqueID;
    }

    private String generateSwiftCode(int uniqueID) {
        return "SWIFT" + uniqueID;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) throws Exception{
        if (this.balance < amount)
            throw new InsufficientFundsException();
        this.balance -= amount;
    }

    public void transfer(Account account, double amount) throws Exception {
        this.withdraw(amount);
        account.deposit(amount);
    }

    public void removeCard(Card card) {
    	this.card = null;
    }

    public Card getCard() {
    	return this.card;
    }

    public void setCard(Card card) {
    	this.card = card;
    }

    public List<Transaction> filterTransactions(List<Transaction> transactions) {
        List<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getFromIBAN().equals(this.IBAN) || transaction.getToIBAN().equals(this.IBAN))
                filteredTransactions.add(transaction);
        }
        return filteredTransactions;
    }


    @Override
    public String toString() {
        return "Account{" +
                "accountID='" + accountID + '\'' +
                ", IBAN='" + IBAN + '\'' +
                ", swiftCode='" + swiftCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", customerID=" + customerID +
                ", card=" + (card != null ? card.toString() : "null") +
                '}';
    }

    public String toCSV() {
        return "Account," + IBAN + "," + swiftCode + "," + bankName + "," + name + "," + balance + "," + customerID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID, IBAN, swiftCode, bankName, name, balance, customerID, card);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Account && ((Account) obj).accountID == this.accountID;
    }
}
