package model.banking;

import exceptions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class Transaction {
    private int transactionID;
    private String fromIBAN;
    private String toIBAN;
    private double amount;
    private String description;
    private Date date;

    public Transaction(String fromIBAN, String toIBAN, double amount, String description) throws Exception {
        if (fromIBAN == null || toIBAN == null || description == null)
            throw new NullValueException();
        if (amount <= 0)
            throw new NegativeValueException();
        this.fromIBAN = fromIBAN;
        this.toIBAN = toIBAN;
        this.amount = amount;
        this.description = description;
        this.date = new Date();
    }

    public Transaction(String fromIBAN, String toIBAN, double amount, String description, Date date) throws Exception {
        if (fromIBAN == null || toIBAN == null || description == null || date == null)
            throw new NullValueException();
        if (amount <= 0)
            throw new NegativeValueException();
        this.fromIBAN = fromIBAN;
        this.toIBAN = toIBAN;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Transaction(int transactionID, ResultSet in) {
        this.transactionID = transactionID;
        this.read(in);
    }

    public Transaction(int transactionID, Scanner in) throws ParseException {
        this.transactionID = transactionID;
        this.read(in);
    }

    public void read(ResultSet in) {
        try {
            this.fromIBAN = in.getString("fromIBAN");
            this.toIBAN = in.getString("toIBAN");
            this.amount = in.getDouble("amount");
            this.description = in.getString("description");
            this.date = in.getDate("date");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void read(Scanner in) {
        System.out.println("From IBAN: ");
        this.fromIBAN = in.nextLine();
        System.out.println("To IBAN: ");
        this.toIBAN = in.nextLine();
        System.out.println("Amount: ");
        this.amount = in.nextDouble();
        in.nextLine();
        System.out.println("Description: ");
        this.description = in.nextLine();
        this.date = new Date();
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public String getFromIBAN() {
        return fromIBAN;
    }

    public void setFromIBAN(String fromIBAN) {
        this.fromIBAN = fromIBAN;
    }

    public String getToIBAN() {
        return toIBAN;
    }

    public void setToIBAN(String toIBAN) {
        this.toIBAN = toIBAN;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID='" + transactionID + '\'' +
                ", from='" + fromIBAN + '\'' +
                ", to='" + toIBAN + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }

    public String toCSV() {
        return "Transaction," + fromIBAN + "," + toIBAN + "," + amount + "," + description + "," + date;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionID, fromIBAN, toIBAN, amount, description, date);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Transaction && this.transactionID == ((Transaction) obj).transactionID;
    }
}
