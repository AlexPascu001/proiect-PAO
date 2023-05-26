package model.card;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Scanner;
import java.util.function.Supplier;

public class CardFactory {
private static int id = 1;

    public Card createCard(String type, ResultSet in) {
        Supplier<Card> cardSupplier = switch (type) {
            case "Visa" -> () -> new Visa(id++, in);
            case "MasterCard" -> () -> new MasterCard(id++, in);
            default -> null;
        };
        if (cardSupplier != null) {
            return cardSupplier.get();
        }
        return null;
    }

    public Card createCard(String type, Scanner in) {
        switch (type) {
            case "Visa" -> {
                try {
                    return new Visa(id++, in);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            case "MasterCard" -> {
                try {
                    return new MasterCard(id++, in);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
