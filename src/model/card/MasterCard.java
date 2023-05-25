package model.card;

import java.sql.ResultSet;
import java.text.ParseException;
import java.util.Scanner;

public class MasterCard extends Card {
    public MasterCard(int cardID, String IBAN) {
        super(cardID, IBAN);
    }

    public MasterCard(int cardID, ResultSet in) {
        super(cardID, in);
    }

    public MasterCard(int cardID, Scanner in) throws ParseException {
        super(cardID, in);
    }

    @Override
    public double fee() {
        return 5;
    }
}
