package Banking;

public class Account {
    protected String IBAN;
    protected String swiftCode;
    protected String bankName;
    protected String name;
    protected double balance;
    final protected int customerID;
//    protected List<Card> cards;


    public Account(String IBAN, String swiftCode, String bankName, String name, double balance, int customerID) {
        this.IBAN = IBAN;
        this.swiftCode = swiftCode;
        this.bankName = bankName;
        this.name = name;
        this.balance = balance;
        this.customerID = customerID;
    }

    public Account(String name, int customerID, int uniqueID) {
        this.name = name;
        this.customerID = customerID;
        this.IBAN = generateIBAN(uniqueID);
        this.swiftCode = generateSwiftCode(uniqueID);
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
            throw new Exception("You do not have enough money!");
        this.balance -= amount;
    }

    public void transfer(Account account, double amount) throws Exception {
        this.withdraw(amount);
        account.deposit(amount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "IBAN='" + IBAN + '\'' +
                ", swiftCode='" + swiftCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                ", customerID=" + customerID +
                '}';
    }

    public String toCSV() {
        return IBAN + "," + swiftCode + "," + bankName + "," + name + "," + balance + "," + customerID;
    }
}
