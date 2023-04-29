package model.banking;

import java.util.Date;

public class SavingsAccount extends Account{
    private double interestRate;
    private final Date startDate;

    public SavingsAccount(String name, int customerId, int uniqueId) {
        super(name, customerId, uniqueId);
        this.startDate = new Date();
        this.interestRate = 2.5;
    }

    public SavingsAccount(String name, int customerId, int uniqueId, double interestRate) {
        super(name, customerId, uniqueId);
        this.startDate = new Date();
        this.interestRate = interestRate;
    }

    public SavingsAccount(String IBAN, String swiftCode, String bankName, String name, double balance, int customerID, double interestRate, Date startDate) {
        super(IBAN, swiftCode, bankName, name, balance, customerID);
        this.interestRate = interestRate;
        this.startDate = startDate;
    }

    public SavingsAccount(String IBAN, String swiftCode, String bankName, String name, double balance, int customerID, double interestRate) {
        super(IBAN, swiftCode, bankName, name, balance, customerID);
        this.interestRate = interestRate;
        this.startDate = new Date();
    }


    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void addInterest() {
        this.balance += this.balance * this.interestRate / 100;
    }

    public double getInterestPerYear() {
        Date currentDate = new Date();
        long diff = currentDate.getTime() - this.startDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return this.balance * this.interestRate / 100 * diffDays / 365;
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "interestRate=" + interestRate +
                ", startDate=" + startDate +
                ", IBAN='" + IBAN + '\'' +
                ", swiftCode='" + swiftCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", customerID=" + customerID +
                '}';
    }

    public String toCSV() {
        return "SavingsAccount" + IBAN + "," + swiftCode + "," + bankName + "," + name + "," + balance + "," + customerID + "," + interestRate + "," + startDate;
    }
}
