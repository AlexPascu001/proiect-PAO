package model.card;

import java.sql.ResultSet;
import java.util.function.Supplier;

public class CardFactory {
private static int id = 0;

    public Card createCard(String type, ResultSet in) {
        Supplier<Card> cardSupplier = null;
        switch (type) {
            case "Visa":
                cardSupplier = () -> new Visa(id++, in);
                break;
            case "MasterCard":
                cardSupplier = () -> new MasterCard(id++, in);
                break;
        }
        return cardSupplier.get();
    }
}
