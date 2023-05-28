package service;
import model.banking.*;
import model.customer.*;
import model.card.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BankService {
    private List<Customer> customers;
    private List<Account> accounts;
    private List<Transaction> transactions;
    private List<Card> cards;

    private final CustomerFactory customerFactory = new CustomerFactory();
    private final AccountFactory accountFactory = new AccountFactory();
    private final TransactionFactory transactionFactory = new TransactionFactory();
    private final CardFactory cardFactory = new CardFactory();

    private final CustomerService customerService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final CardService cardService;

    private final List<Integer> customerIDs;
    private final List<Integer> accountIDs;
    private final List<Integer> transactionIDs;
    private final List<Integer> cardIDs;

    private static BankService instance = null;

    private BankService(CustomerService customerService, AccountService accountService, TransactionService transactionService, CardService cardService) {
        this.customerService = customerService;
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.cardService = cardService;

        this.customers = customerService.read();
        this.accounts = accountService.read();
        this.transactions = transactionService.read();
        this.cards = cardService.read();

        // null check
        if (this.customers == null) {
            this.customers = new ArrayList<>();
        }
        if (this.accounts == null) {
            this.accounts = new ArrayList<>();
        }
        if (this.transactions == null) {
            this.transactions = new ArrayList<>();
        }
        if (this.cards == null) {
            this.cards = new ArrayList<>();
        }
        // some functional programming
        // toList return immutable list, so we need to create a new list
        this.customerIDs = new ArrayList<>(this.customers.stream().map(Customer::getCustomerID).toList());
        this.accountIDs = new ArrayList<>(this.accounts.stream().map(Account::getAccountID).toList());
        this.transactionIDs = new ArrayList<>(this.transactions.stream().map(Transaction::getTransactionID).toList());
        this.cardIDs = new ArrayList<>(this.cards.stream().map(Card::getCardID).toList());
    }

    public static BankService getInstance(CustomerService customerService, AccountService accountService, TransactionService transactionService, CardService cardService) {
        if (instance == null) {
            instance = new BankService(customerService, accountService, transactionService, cardService);
        }
        return instance;
    }

    public List<Customer> getCustomers() {
        customers = customerService.read();
        if (customers == null || customers.isEmpty()) {
            System.out.println("No customers found");
        }
        return customers;
    }

    public List<Account> getAccounts() {
        accounts = accountService.read();
        if (accounts == null || accounts.isEmpty()) {
            System.out.println("No accounts found");
        }
        return accounts;
    }

    public List<Transaction> getTransactions() {
        transactions = transactionService.read();
        if (transactions == null || transactions.isEmpty()) {
            System.out.println("No transactions found");
        }
        return transactions;
    }

    public List<Card> getCards() {
        cards = cardService.read();
        if (cards == null || cards.isEmpty()) {
            System.out.println("No cards found");
        }
        return cards;
    }


    public void createCustomer(Scanner in) throws ParseException {
        System.out.println("createCustomer <firstName> <lastName> <CNP> <phoneNumber> <email> <birthDate> <address>");
        Customer customer = customerFactory.createCustomer(in);
        this.customerService.create(customer);
        this.customers.add(customer);
        this.customerIDs.add(customer.getCustomerID());
        System.out.println("Customer created successfully");
    }

    public void createAccount(Scanner in) throws ParseException {
        System.out.println("createAccount <IBAN> <swiftCode> <bankName> <name> <balance> <customerID>");
        Account account = accountFactory.createAccount(in);
        if (!this.customerIDs.contains(account.getCustomerID())) {
            System.out.println("Invalid customer ID");
            return;
        }
        int customerIndex = this.customerIDs.indexOf(account.getCustomerID());
        this.customers.get(customerIndex).addAccount(account);
        this.accountService.create(account);
        this.accounts.add(account);
        this.accountIDs.add(account.getAccountID());
        System.out.println("Account created successfully");
    }


    public void createTransaction(Scanner in) throws ParseException {
        System.out.println("createTransaction <fromIBAN> <toIBAN> <amount> <description>");
        Transaction transaction = transactionFactory.createTransaction(in);
        this.transactions.add(transaction);
        this.transactionService.create(transaction);
        this.transactionIDs.add(transaction.getTransactionID());
        System.out.println("Transaction created successfully");
    }

    public void getAccounts(Scanner in) {
        System.out.println("getAccounts <customerID>");
        System.out.println("Customer ID: ");
        int customerID = in.nextInt();

        if (!this.customerIDs.contains(customerID)) {
            System.out.println("Invalid customer ID");
            return;
        }

        List<Account> accounts = this.accountService.readCustomer(customerID);

        if (accounts == null) {
            System.out.println("No accounts found");
            return;
        }

        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    public void createCard(Scanner in) throws ParseException {
        System.out.println("createCard <type> <IBAN>");
        System.out.println("Type: (Visa/MasterCard)");
        String type = in.next();
        in.nextLine();
        if (!type.equals("Visa") && !type.equals("MasterCard")) {
            System.out.println("Invalid card type");
            return;
        }
        Card card = cardFactory.createCard(type, in);
        this.cards.add(card);
        this.cardIDs.add(card.getCardID());
        if (Objects.equals(type, "MasterCard"))
            this.cardService.create((MasterCard) card);
        else if (Objects.equals(type, "Visa"))
            this.cardService.create((Visa) card);
        else {
            System.out.println("Invalid card type");
            return;
        }

        System.out.println("Card created successfully");
    }

    public void getBalance(Scanner in) {
        System.out.println("getBalance <accountID>");
        System.out.println("Account ID: ");
        int accountID = in.nextInt();

        if (!this.accountIDs.contains(accountID)) {
            System.out.println("Invalid account ID");
            return;
        }

        Account account = this.accountService.read(accountID);
        System.out.println("Balance: " + account.getBalance());
    }

    public void deposit(Scanner in) {
        System.out.println("deposit <accountID> <amount>");
        System.out.println("Account ID: ");
        int accountID = in.nextInt();

        if (!this.accountIDs.contains(accountID)) {
            System.out.println("Invalid account ID");
            return;
        }

        Account account = this.accountService.read(accountID);
        System.out.println("Amount: ");
        double amount = in.nextDouble();
        account.deposit(amount);
        this.accountService.update(account);
        System.out.println("Deposit successful");
    }

    public void withdraw(Scanner in) throws Exception {
        System.out.println("withdraw <accountID> <amount>");
        System.out.println("Account ID: ");
        int accountID = in.nextInt();

        if (!this.accountIDs.contains(accountID)) {
            System.out.println("Invalid account ID");
            return;
        }

        Account account = this.accountService.read(accountID);
        System.out.println("Amount: ");
        double amount = in.nextDouble();
        account.withdraw(amount);
        this.accountService.update(account);
        System.out.println("Withdraw successful");
    }

    public void transfer(Scanner in) throws Exception {
        System.out.println("transfer <fromAccountID> <toAccountID> <amount>");
        System.out.println("From Account ID: ");
        int fromAccountID = in.nextInt();
        if (!this.accountIDs.contains(fromAccountID)) {
            System.out.println("Invalid fromAccount ID");
            return;
        }
        System.out.println("To Account ID: ");
        int toAccountID = in.nextInt();
        if (!this.accountIDs.contains(toAccountID)) {
            System.out.println("Invalid toAccount ID");
            return;
        }

        Account fromAccount = this.accountService.read(fromAccountID);
        Account toAccount = this.accountService.read(toAccountID);

        System.out.println("Amount: ");
        double amount = in.nextDouble();
        fromAccount.transfer(toAccount, amount);
        this.accountService.update(fromAccount);
        this.accountService.update(toAccount);
        System.out.println("Transfer successful");
    }

    public void getAddress(Scanner in) {
        System.out.println("getAddress <customerID>");
        System.out.println("Customer ID: ");
        int customerID = in.nextInt();

        if (!this.customerIDs.contains(customerID)) {
            System.out.println("Invalid customer ID");
            return;
        }

        Customer customer = this.customerService.read(customerID);
        System.out.println("Address: " + customer.getAddress());
    }

    public void getTransactions(Scanner in) {
        System.out.println("getTransactions <accountID>");
        System.out.println("Account ID: ");
        int accountID = in.nextInt();

        if (!this.accountIDs.contains(accountID)) {
            System.out.println("Invalid account ID");
            return;
        }

        Account account = this.accounts.get(accountID - 1);
        List<Transaction> filteredTransactions = account.filterTransactions(this.transactions);
        System.out.println("Transactions: ");
        for (Transaction transaction : filteredTransactions) {
            System.out.println(transaction);
        }
    }

    public void updateCustomer(Scanner in) throws ParseException {
        System.out.println("updateCustomer <customerID> <firstName> <lastName> <CNP> <phoneNumber> <email> <birthDate> <address>");
        System.out.println("Customer ID: ");
        int customerID = in.nextInt();

        if (!this.customerIDs.contains(customerID)) {
            System.out.println("Invalid customer ID");
            return;
        }

        Customer customer = this.customerService.read(customerID);
        int index = this.customers.indexOf(customer);
        System.out.println("First name: ");
        customer.setFirstName(in.next());
        System.out.println("Last name: ");
        customer.setLastName(in.next());
        System.out.println("CNP: ");
        customer.setCNP(in.next());
        System.out.println("Phone number: ");
        customer.setPhoneNumber(in.next());
        System.out.println("Email: ");
        customer.setEmail(in.next());
        System.out.println("Birth date: (yyyy-MM-dd)");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(in.next());
        customer.setBirthDate(date);
        in.nextLine();
        Address address = new Address(in);
        customer.setAddress(address);
        this.customers.set(index, customer);
        this.customerService.update(customer);
        System.out.println("Customer updated successfully");
    }

    public void updateAccount(Scanner in) {
        System.out.println("updateAccount <accountID> <IBAN> <swiftCode> <bankName> <name> <balance>");
        System.out.println("Account ID: ");
        int accountID = in.nextInt();
        in.nextLine();
        if (!this.accountIDs.contains(accountID)) {
            System.out.println("Invalid account ID");
            return;
        }

        Account account = this.accountService.read(accountID);
        int index = this.accounts.indexOf(account);

        System.out.println("IBAN: ");
        account.setIBAN(in.nextLine());
        System.out.println("Swift code: ");
        account.setSwiftCode(in.nextLine());
        System.out.println("Bank name: ");
        account.setBankName(in.nextLine());
        System.out.println("Name: ");
        account.setName(in.nextLine());
        System.out.println("Balance: ");
        account.setBalance(in.nextDouble());
        in.nextLine();
        this.accounts.set(index, account);
        this.accountService.update(account);
        System.out.println("Account updated successfully");
    }

    public void updateCard(Scanner in) throws ParseException {
        System.out.println("updateCard <cardID> <type> <IBAN>");
        System.out.println("Card ID: ");
        int cardID = in.nextInt();
        if (!this.cardIDs.contains(cardID)) {
            System.out.println("Invalid card ID");
            return;
        }

        Card card = this.cardService.read(cardID);
        int index = this.cards.indexOf(card);
        System.out.println("Type: ");
        String type = in.next();
        in.nextLine();
        Card newCard = cardFactory.createCard(type, in);
        newCard.setCardID(cardID);
        this.cards.set(index, newCard);
        this.cardService.update(newCard, type);
        System.out.println("Card updated successfully");
    }

    public void updateTransaction(Scanner in)  throws ParseException {
        System.out.println("updateTransaction <transactionID> <fromIBAN> <toIBAN> <amount> <description> <date>");
        System.out.println("Transaction ID: ");
        int transactionID = in.nextInt();

        if (!this.transactionIDs.contains(transactionID)) {
            System.out.println("Invalid transaction ID");
            return;
        }

        Transaction transaction = this.transactionService.read(transactionID);
        int index = this.transactions.indexOf(transaction);
        System.out.println("From IBAN: ");
        transaction.setFromIBAN(in.next());
        System.out.println("To IBAN: ");
        transaction.setToIBAN(in.next());
        System.out.println("Amount: ");
        transaction.setAmount(in.nextDouble());
        in.nextLine();
        System.out.println("Description: ");
        transaction.setDescription(in.nextLine());
        System.out.println("Date: (yyyy-MM-dd)");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(in.next());
        transaction.setDate(date);
        this.transactionService.update(transaction);
        this.transactions.set(index, transaction);
        System.out.println("Transaction updated successfully");
    }

    public void deleteCustomer(Scanner in) {
        System.out.println("deleteCustomer <customerID>");
        System.out.println("Customer ID: ");
        int customerID = in.nextInt();

        if (!this.customerIDs.contains(customerID)) {
            System.out.println("Invalid customer ID");
            return;
        }

        Customer customer = this.customerService.read(customerID);
        this.customers.remove(customer);
        this.customerService.delete(customer);
        // also delete all accounts associated with this customer
        List<Account> toRemove = new ArrayList<>();
        for (Account account : this.accounts) {
            if (account.getCustomerID() == customerID) {
                toRemove.add(account);
                int index = this.accountIDs.indexOf(account.getAccountID());
                this.accountIDs.remove(index);
            }
        }
        this.accounts.removeAll(toRemove);
        this.customerIDs.remove(this.customerIDs.indexOf(customerID));
        System.out.println("Customer deleted successfully");
    }

    public void deleteAccount(Scanner in) {
        System.out.println("deleteAccount <accountID>");
        System.out.println("Account ID: ");
        int accountID = in.nextInt();

        if (!this.accountIDs.contains(accountID)) {
            System.out.println("Invalid account ID");
            return;
        }

        Account account = this.accountService.read(accountID);
        this.accounts.remove(account);
        this.accountService.delete(account);
        this.accountIDs.remove(this.accountIDs.indexOf(accountID));
        System.out.println("Account deleted successfully");
    }

    public void deleteCard(Scanner in) {
        System.out.println("deleteCard <cardID>");
        System.out.println("Card ID: ");
        int cardID = in.nextInt();

        if (!this.cardIDs.contains(cardID)) {
            System.out.println("Invalid card ID");
            return;
        }

        Card card = this.cardService.read(cardID);
        this.cards.remove(card);
        this.cardService.delete(card);
        this.cardIDs.remove(this.cardIDs.indexOf(cardID));
        System.out.println("Card deleted successfully");
    }

    public void deleteTransaction(Scanner in) {
        System.out.println("deleteTransaction <transactionID>");
        System.out.println("Transaction ID: ");
        int transactionID = in.nextInt();

        if (!this.transactionIDs.contains(transactionID)) {
            System.out.println("Invalid transaction ID");
            return;
        }

        Transaction transaction = this.transactionService.read(transactionID);
        this.transactions.remove(transaction);
        this.transactionService.delete(transaction);
        this.transactionIDs.remove(this.transactionIDs.indexOf(transactionID));
        System.out.println("Transaction deleted successfully");
    }

    public void getCustomer(Scanner in) {
        System.out.println("getCustomer <customerID>");
        System.out.println("Customer ID: ");
        int customerID = in.nextInt();

        if (!this.customerIDs.contains(customerID)) {
            System.out.println("Invalid customer ID");
            return;
        }

        Customer customer = this.customerService.read(customerID);
        System.out.println("Customer: " + customer);
    }

    public void getAccount(Scanner in) {
        System.out.println("getAccount <accountID>");
        System.out.println("Account ID: ");
        int accountID = in.nextInt();

        if (!this.accountIDs.contains(accountID)) {
            System.out.println("Invalid account ID");
            return;
        }

        Account account = this.accountService.read(accountID);
        System.out.println("Account: " + account);
    }

    public void getCard(Scanner in) {
        System.out.println("getCard <cardID>");
        System.out.println("Card ID: ");
        int cardID = in.nextInt();

        if (!this.cardIDs.contains(cardID)) {
            System.out.println("Invalid card ID");
            return;
        }

        Card card = this.cardService.read(cardID);
        System.out.println("Card: " + card);
    }

    public void getTransaction(Scanner in) {
        System.out.println("getTransaction <transactionID>");
        System.out.println("Transaction ID: ");
        int transactionID = in.nextInt();

        if (!this.transactionIDs.contains(transactionID)) {
            System.out.println("Invalid transaction ID");
            return;
        }

        Transaction transaction = this.transactionService.read(transactionID);
        System.out.println("Transaction: " + transaction);
    }


}
