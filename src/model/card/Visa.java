package model.card;

public class Visa extends Card {
    public Visa(int cardID, String IBAN) {
        super(cardID, IBAN);
    }

    @Override
    public double fee() {
        return 10;
    }
}
