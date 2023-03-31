package Banking;

import java.util.Date;

public class Transaction {
    private final String fromIBAN;
    private final String toIBAN;
    private final double amount;
    private final String description;
    private final Date date;

    public Transaction(String fromIBAN, String toIBAN, double amount, String description) throws Exception {
        if (fromIBAN == null || toIBAN == null || description == null)
            throw new Exception("Null value not allowed");
        if (amount < 0)
            throw new Exception("Amount cannot be negative");
        this.fromIBAN = fromIBAN;
        this.toIBAN = toIBAN;
        this.amount = amount;
        this.description = description;
        this.date = new Date();
    }

    public Transaction(String fromIBAN, String toIBAN, double amount, String description, Date date) throws Exception {
        if (fromIBAN == null || toIBAN == null || description == null || date == null)
            throw new Exception("Null value not allowed");
        if (amount < 0)
            throw new Exception("Amount cannot be negative");
        this.fromIBAN = fromIBAN;
        this.toIBAN = toIBAN;
        this.amount = amount;
        this.description = description;
        this.date = date;
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
        return fromIBAN + "," + toIBAN + "," + amount + "," + description + "," + date;
    }
}
