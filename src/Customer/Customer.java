package Customer;

import Banking.Account;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
    private final int customerID;
    private String firstName;
    private String lastName;
    private String CNP;
    private Address address;
    private String phoneNumber;
    private String email;
    private Date birthDate;

    List<Account> accounts = new ArrayList<Account>();

    public Customer(int customerID, String firstName, String lastName, String CNP, Address address, String phoneNumber, String email, Date birthDate, List<Account> accounts) throws Exception {
        if (firstName == null || lastName == null || CNP == null || address == null || phoneNumber == null || email == null || birthDate == null)
            throw new IllegalArgumentException("Null value not allowed");
        if ((CNP.length() != 13) || (phoneNumber.length() != 10))
            throw new IllegalArgumentException("Invalid CNP or phone number");
        if (customerID < 0)
            throw new IllegalArgumentException("Customer ID cannot be negative");
        if ((new Date()).getTime()- birthDate.getTime() < 18L * 365 * 24 * 60 * 60 * 1000)
            throw new IllegalArgumentException("Customer must be at least 18 years old");
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
            throw new IllegalArgumentException("Null value not allowed");
        if ((CNP.length() != 13) || (phoneNumber.length() != 10))
            throw new IllegalArgumentException("Invalid CNP or phone number");
        if (customerID < 0)
            throw new IllegalArgumentException("Customer ID cannot be negative");
        if ((new Date()).getTime()- birthDate.getTime() < 18L * 365 * 24 * 60 * 60 * 1000)
            throw new IllegalArgumentException("Customer must be at least 18 years old");
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.CNP = CNP;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthDate = birthDate;
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

}
