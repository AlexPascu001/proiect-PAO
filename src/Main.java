import Service.BankService;
import Banking.*;
import Customer.*;
import Card.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
public class Main {
    public static void main(String[] args) throws Exception {
        BankService BankService = new BankService();
        Address address = new Address("Aleea Moldovita", "Bucuresti", "Romania", 123456);
        Calendar c = Calendar.getInstance();
        c.set(2002, Calendar.MARCH, 30);
        Customer customer = BankService.createCustomer("Alex", "Pascu", "5020330161695", address, "0768323343", "alex@alex.com", c.getTime());
        System.out.println(customer.toString());
        Card card = BankService.createCard("RO123456789");
        System.out.println(card.toString());
        Account account = BankService.createAccount("RO123456789", "BRDROU", "BRD", "Cont personal", 10000.0, 1);
        SavingsAccount savingsAccount = BankService.createSavingsAccount("RO123456789", "BRDROU", "BRD", "Cont economii", 10000.0, 1, 1, c.getTime());
        Transaction transaction = BankService.createTransaction("RO123456789", "RO987654321", 1000.0, "Transfer", c.getTime());
        System.out.println(account.toString());
        System.out.println(account.getBalance());
        account.deposit(1000.0);
        System.out.println(account.getBalance());
        account.withdraw(5000);
        System.out.println(account.getBalance());
        account.setBalance(0);
        System.out.println(account.getBalance());
        System.out.println(savingsAccount.toString());
        System.out.println(savingsAccount.getBalance());
        System.out.println(savingsAccount.getInterestRate());
        System.out.println(savingsAccount.getInterestPerYear());
        System.out.println(transaction.toString());
        System.out.println(transaction.getAmount());
        System.out.println(account.filterTransactions(new ArrayList<>() {{
            add(transaction);
        }}));
    }
}