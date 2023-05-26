package model.card;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Scanner;

public class Visa extends Card {
    public Visa(int cardID, String IBAN) {
        super(cardID, IBAN);
    }

    public Visa(int cardID, ResultSet in) {
        super(cardID, in);
    }

    public Visa(int cardID, Scanner in) throws ParseException {
        super(cardID, in);
    }

    @Override
    public double fee() {
        return 10;
    }

    @Override
    public String toString() {
        return super.toString() + ", type=Visa, " + "fee=" + fee() + "}";
    }
}
