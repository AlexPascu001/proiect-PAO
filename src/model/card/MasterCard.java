package model.card;

import java.sql.ResultSet;

public class MasterCard extends Card {
    public MasterCard(int cardID, String IBAN) {
        super(cardID, IBAN);
    }

    public MasterCard(int cardID, ResultSet in) {
        super(cardID, in);
    }

    @Override
    public double fee() {
        return 5;
    }
}
