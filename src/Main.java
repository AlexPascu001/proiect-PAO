import service.*;
import model.banking.*;
import model.customer.*;
import model.card.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class Main {
    static final List<String> commands = Arrays.asList(
            "createCustomer",
            "createAccount",
            "createTransaction",
            "createCard",
            "getCustomer",
            "getAccount",
            "getAccounts",
            "getTransaction",
            "getCard",
            "readCustomers",
            "readAccounts",
            "readTransactions",
            "readCards",
            "deposit",
            "withdraw",
            "transfer",
            "getBalance",
            "updateCustomer",
            "updateAccount",
            "updateTransaction",
            "updateCard",
            "deleteCustomer",
            "deleteAccount",
            "deleteTransaction",
            "deleteCard",
            "help",
            "exit"
    );

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/ProiectPAO";
            String username = "alex";
            String password = "alex";
            return DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Connection connection = Main.getConnection();
        if (connection == null) {
            System.out.println("Connection failed!");
            return;
        }

        CustomerService customerService = CustomerService.getInstance(connection);
        AccountService accountService = AccountService.getInstance(connection);
        TransactionService transactionService = TransactionService.getInstance(connection);
        CardService cardService = CardService.getInstance(connection);
        AuditService auditService = new AuditService("data\\audit.csv");
        BankService bankService = BankService.getInstance(customerService, accountService, transactionService, cardService);


        while (true) {
            System.out.println("Please type your command: (type 'help' for a list of commands)");
            String command = in.nextLine();
            try {
                switch (command) {
                    case "createCustomer" -> bankService.createCustomer(in);
                    case "createAccount" -> bankService.createAccount(in);
                    case "createTransaction" -> bankService.createTransaction(in);
                    case "createCard" -> bankService.createCard(in);
                    case "getCustomer" -> bankService.getCustomer(in);
                    case "getAccount" -> bankService.getAccount(in);
                    case "getAccounts" -> bankService.getAccounts(in);
                    case "getTransaction" -> bankService.getTransaction(in);
                    case "getCard" -> bankService.getCard(in);
                    case "readCustomers" -> bankService.getCustomers().forEach(System.out::println);
                    case "readAccounts" -> bankService.getAccounts().forEach(System.out::println);
                    case "readTransactions" -> bankService.getTransactions().forEach(System.out::println);
                    case "readCards" -> bankService.getCards().forEach(System.out::println);
                    case "updateCustomer" -> bankService.updateCustomer(in);
                    case "updateAccount" -> bankService.updateAccount(in);
                    case "updateTransaction" -> bankService.updateTransaction(in);
                    case "updateCard" -> bankService.updateCard(in);
                    case "deleteCustomer" -> bankService.deleteCustomer(in);
                    case "deleteAccount" -> bankService.deleteAccount(in);
                    case "deleteTransaction" -> bankService.deleteTransaction(in);
                    case "deleteCard" -> bankService.deleteCard(in);
                    case "getBalance" -> bankService.getBalance(in);
                    case "deposit" -> bankService.deposit(in);
                    case "withdraw" -> bankService.withdraw(in);
                    case "transfer" -> bankService.transfer(in);
                    case "help" -> commands.forEach(System.out::println);
                    case "exit" -> {
                        return;
                    }
                }
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }
            finally {
                auditService.logAction(command);
            }
        }
    }
}