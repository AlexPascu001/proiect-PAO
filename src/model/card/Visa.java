package model.card;

import java.sql.ResultSet;

public class Visa extends Card {
    public Visa(int cardID, String IBAN) {
        super(cardID, IBAN);
    }

    public Visa(int cardID, ResultSet in) {
        super(cardID, in);
    }

    @Override
    public double fee() {
        return 10;
    }
}
