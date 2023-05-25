package model.customer;
import model.banking.Account;
import exceptions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Customer {
    private int customerID;
    private String firstName;
    private String lastName;
    private String CNP;
    private Address address;
    private String phoneNumber;
    private String email;
    private Date birthDate;

    List<Account> accounts = new ArrayList<>();

    public Customer(int customerID, String firstName, String lastName, String CNP, Address address, String phoneNumber, String email, Date birthDate, List<Account> accounts) throws Exception {
        if (firstName == null || lastName == null || CNP == null || address == null || phoneNumber == null || email == null || birthDate == null)
            throw new NullValueException();
        if (CNP.length() != 13)
            throw new InvalidCNPException();
        if (phoneNumber.length() != 10)
            throw new InvalidPhoneNumberException();
        if (customerID < 0)
            throw new NegativeValueException();
        if ((new Date()).getTime()- birthDate.getTime() < 18L * 365 * 24 * 60 * 60 * 1000)
            throw new IllegalAgeException();
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDate = birthDate;
        this.accounts = accounts;
    }

    public Customer(int customerID, String firstName, String lastName, String CNP, Address address, String phoneNumber, String email, Date birthDate) throws Exception {
        if (firstName == null || lastName == null || CNP == null || address == null || phoneNumber == null || email == null || birthDate == null)
            throw new NullValueException();
        if (CNP.length() != 13)
            throw new InvalidCNPException();
        if (phoneNumber.length() != 10)
            throw new InvalidPhoneNumberException();
        if (customerID < 0)
            throw new NegativeValueException();
        if ((new Date()).getTime()- birthDate.getTime() < 18L * 365 * 24 * 60 * 60 * 1000)
            throw new IllegalAgeException();
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDate = birthDate;
    }

    public Customer(int customerID, ResultSet in) {
        this.customerID = customerID;
        this.read(in);
    }

    public Customer(int customerID, Scanner in) throws Exception {
        this.customerID = customerID;
        this.read(in);
    }

    private void read(ResultSet in) {
        try {
            this.firstName = in.getString("firstName");
            this.lastName = in.getString("lastName");
            this.CNP = in.getString("CNP");
            this.address = new Address(in);
            this.phoneNumber = in.getString("phone");
            this.email = in.getString("email");
            this.birthDate = in.getDate("birthDate");
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    private void read(Scanner in) throws Exception {
        System.out.println("First name: ");
        this.firstName = in.nextLine();
        System.out.println("Last name: ");
        this.lastName = in.nextLine();
        System.out.println("CNP: ");
        // TODO: validate CNP (length, digits only)
        this.CNP = in.nextLine();
        if (this.CNP.length() != 13)
            throw new InvalidCNPException();
        if (this.CNP.chars().anyMatch(c -> !Character.isDigit(c)))
            throw new InvalidCNPException();
        System.out.println("Phone number: ");
        this.phoneNumber = in.nextLine();
        if (this.phoneNumber.length() != 10)
            throw new InvalidPhoneNumberException();
        if (this.phoneNumber.chars().anyMatch(c -> !Character.isDigit(c)))
            throw new InvalidPhoneNumberException();
        System.out.println("Email: ");
        this.email = in.nextLine();
        System.out.println("Birth date: (yyyy-MM-dd)");
        this.birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(in.nextLine());
        System.out.println("Address: ");
        this.address = new Address(in);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", CNP='" + CNP + '\'' +
                ", address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", accounts=" + accounts +
                '}';
    }

    public String toCSV() {
        return "Customer," + customerID + "," + firstName + "," + lastName + "," + CNP + "," + address.toCSV() + "," + phoneNumber + "," + email + "," + birthDate;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
    }

    public void removeAccount(String IBAN) {
        for (Account account : accounts) {
            if (account.getIBAN().equals(IBAN)) {
                accounts.remove(account);
                break;
            }
        }
    }
    public Account getAccount(String IBAN) {
        for (Account account : accounts) {
            if (account.getIBAN().equals(IBAN)) {
                return account;
            }
        }
        return null;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerID, firstName, lastName, CNP, address, phoneNumber, email, birthDate, accounts);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Customer && ((Customer) obj).customerID == this.customerID;
    }
}
