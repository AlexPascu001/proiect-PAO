package model.card;

public class MasterCard extends Card {
    public MasterCard(int cardID, String IBAN) {
        super(cardID, IBAN);
    }
    @Override
    public double fee() {
        return 5;
    }
}
