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

    private CustomerService customerService;
    private AccountService accountService;
    private TransactionService transactionService;
    private CardService cardService;

    private List<Integer> customerIDs;
    private List<Integer> accountIDs;
    private List<Integer> transactionIDs;
    private List<Integer> cardIDs;

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

        // functional programming
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
        // toList return immutable list so we need to create a new list
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
        return customers;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Card> getCards() {
        return cards;
    }

    //TODO: null check and correct input check
    //TODO: handle exceptions
    //TODO: check errora for other classes

    public void createCustomer(Scanner in) throws ParseException {
        System.out.println("createCustomer <firstName> <lastName> <CNP> <phoneNumber> <email> <birthDate> <address>");
        Customer customer = customerFactory.createCustomer(in);
        this.customerService.create(customer);
        this.customers.add(customer);
        Integer id = Integer.valueOf(customer.getCustomerID());
        this.customerIDs.add(id);
        System.out.println("Customer created successfully");
    }

    public void createAccount(Scanner in) throws ParseException {
        System.out.println("createAccount <IBAN> <swiftCode> <bankName> <name> <balance> <customerID>");
        Account account = accountFactory.createAccount(in);
        if (this.customerIDs.contains(account.getCustomerID()) == false) {
            System.out.println("Invalid customer ID");
            return;
        }
        this.customers.get(customers.indexOf(account.getCustomerID())).addAccount(account);
        this.accountService.create(account);
        this.accounts.add(account);
        this.accountIDs.add(account.getAccountID());
        System.out.println("Account created successfully");
    }

    public void removeAccount(Scanner in) {
        System.out.println("removeAccount <accountID>");
        System.out.println("Account ID: ");
        int accountID = in.nextInt();
        Account account = this.accountService.read(accountID);
        if (account == null) {
            System.out.println("Account not found");
            return;
        }
        this.accounts.remove(account);
        this.customers.get(customers.indexOf(account.getCustomerID())).removeAccount(account);
        this.accountService.delete(account);
        this.accountIDs.remove(account.getAccountID());
        System.out.println("Account removed successfully");
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

        if (this.customerIDs.contains(customerID) == false) {
            System.out.println("Invalid customer ID");
            return;
        }

        List<Account> accounts = this.accountService.readCustomer(customerID);
        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    public void createCard(Scanner in) throws ParseException {
        System.out.println("createCard <type> <IBAN>");
        System.out.println("Type: (Visa/Mastercard)");
        String type = in.next();
        if (type.equals("Visa") == false && type.equals("Mastercard") == false) {
            System.out.println("Invalid card type");
            return;
        }
        Card card = cardFactory.createCard(type, in);
        this.cards.add(card);
        this.cardIDs.add(card.getCardID());
        if (Objects.equals(type, "Mastercard"))
            this.cardService.create((MasterCard) card);
        else if (Objects.equals(type, "Visa"))
            this.cardService.create((Visa) card);
        else
            System.out.println("Invalid card type");

        System.out.println("Card created successfully");
    }

    public void getBalance(Scanner in) {
        System.out.println("getBalance <accountID>");
        System.out.println("Account ID: ");
        int accountID = in.nextInt();

        if (this.accountIDs.contains(accountID) == false) {
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

        if (this.accountIDs.contains(accountID) == false) {
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

        if (this.accountIDs.contains(accountID) == false) {
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
        if (this.accountIDs.contains(fromAccountID) == false) {
            System.out.println("Invalid fromAccount ID");
            return;
        }
        System.out.println("To Account ID: ");
        int toAccountID = in.nextInt();
        if (this.accountIDs.contains(toAccountID) == false) {
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

        if (this.customerIDs.contains(customerID) == false) {
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

        if (this.accountIDs.contains(accountID) == false) {
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

        if (this.customerIDs.contains(customerID) == false) {
            System.out.println("Invalid customer ID");
            return;
        }

        Customer customer = this.customerService.read(customerID);
        int index = this.customers.indexOf(customer);
        System.out.println(index);
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
        System.out.println("updateAccount <accountID> <customerID> <IBAN> <swiftCode> <bankName> <name> <balance>");
        System.out.println("Account ID: ");
        int accountID = in.nextInt();
        if (this.accountIDs.contains(accountID) == false) {
            System.out.println("Invalid account ID");
            return;
        }

        System.out.println("Customer ID: ");
        int customerID = in.nextInt();

        if (this.customerIDs.contains(customerID) == false) {
            System.out.println("Invalid customer ID");
            return;
        }

        Customer customer = this.customerService.read(customerID);
        Account account = this.accountService.read(customerID);
        int index = this.accounts.indexOf(account);
        account.setIBAN(in.next());
        account.setSwiftCode(in.next());
        account.setBankName(in.next());
        account.setName(in.next());
        account.setBalance(in.nextDouble());
        this.accounts.set(index, account);
        this.accountService.update(account);
        System.out.println("Account updated successfully");
    }

    public void updateCard(Scanner in) throws ParseException {
        System.out.println("updateCard <cardID> <type> <IBAN>");
        System.out.println("Card ID: ");
        int cardID = in.nextInt();

        if (this.cardIDs.contains(cardID) == false) {
            System.out.println("Invalid card ID");
            return;
        }

        Card card = this.cardService.read(cardID);
        int index = this.cards.indexOf(card);
        this.cards.remove(card);
        Card newCard = cardFactory.createCard(in.next(), in);
        this.cards.set(index, newCard);
        this.cardService.update(newCard);
        System.out.println("Card updated successfully");
    }

    public void updateTransaction(Scanner in) {
        System.out.println("updateTransaction <transactionID> <fromIBAN> <toIBAN> <amount> <description> <date>");
        System.out.println("Transaction ID: ");
        int transactionID = in.nextInt();

        if (this.transactionIDs.contains(transactionID) == false) {
            System.out.println("Invalid transaction ID");
            return;
        }

        Transaction transaction = this.transactionService.read(transactionID);
        int index = this.transactions.indexOf(transaction);
        this.transactionService.update(transaction);
        this.transactions.set(index, transaction);
        System.out.println("Transaction updated successfully");
    }

    public void deleteCustomer(Scanner in) {
        System.out.println("deleteCustomer <customerID>");
        System.out.println("Customer ID: ");
        int customerID = in.nextInt();

        if (this.customerIDs.contains(customerID) == false) {
            System.out.println("Invalid customer ID");
            return;
        }

        Customer customer = this.customerService.read(customerID);
        this.customers.remove(customer);
        this.customerService.delete(customer);
        this.customerIDs.remove(this.customerIDs.indexOf(customerID));
        System.out.println("Customer deleted successfully");
    }

    public void deleteAccount(Scanner in) {
        System.out.println("deleteAccount <accountID>");
        System.out.println("Account ID: ");
        int accountID = in.nextInt();

        if (this.accountIDs.contains(accountID) == false) {
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

        if (this.cardIDs.contains(cardID) == false) {
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

        if (this.transactionIDs.contains(transactionID) == false) {
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

        if (this.customerIDs.contains(customerID) == false) {
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

        if (this.accountIDs.contains(accountID) == false) {
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

        if (this.cardIDs.contains(cardID) == false) {
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

        if (this.transactionIDs.contains(transactionID) == false) {
            System.out.println("Invalid transaction ID");
            return;
        }

        Transaction transaction = this.transactionService.read(transactionID);
        System.out.println("Transaction: " + transaction);
    }


}
