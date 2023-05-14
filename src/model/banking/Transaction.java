package model.banking;

import exceptions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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

    public int getTransactionID() {
        return transactionID;
    }

    public String getFromIBAN() {
        return fromIBAN;
    }

    public String getToIBAN() {
        return toIBAN;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "from='" + fromIBAN + '\'' +
                ", to='" + toIBAN + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }

    public String toCSV() {
        return "Transaction," + fromIBAN + "," + toIBAN + "," + amount + "," + description + "," + date;
    }
}
