package model.card;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CardService {
    private final Connection connection;
    private final CardFactory cardFactory = new CardFactory();
    private static CardService instance = null;

    private CardService(Connection connection) {
        this.connection = connection;
    }

    public static CardService getInstance(Connection connection) {
        if (instance == null) {
            instance = new CardService(connection);
        }
        return instance;
    }

    public void create(MasterCard masterCard) {
        try {
            String sql = "INSERT INTO Cards (cardID, cardNumber, IBAN, CVV, PIN, expiryDate, type) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, masterCard.getCardID());
            preparedStatement.setString(2, masterCard.getCardNumber());
            preparedStatement.setString(3, masterCard.getIBAN());
            preparedStatement.setInt(4, masterCard.getCVV());
            preparedStatement.setInt(5, masterCard.getPIN());
            preparedStatement.setString(6, new SimpleDateFormat("yyyy-MM-dd").format(masterCard.getExpiryDate()));
            preparedStatement.setString(7, "MasterCard");
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void create(Visa visa) {
        try {
            String sql = "INSERT INTO Cards (cardID, cardNumber, IBAN, CVV, PIN, expiryDate, type) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, visa.getCardID());
            preparedStatement.setString(2, visa.getCardNumber());
            preparedStatement.setString(3, visa.getIBAN());
            preparedStatement.setInt(4, visa.getCVV());
            preparedStatement.setInt(5, visa.getPIN());
            preparedStatement.setString(6, new SimpleDateFormat("yyyy-MM-dd").format(visa.getExpiryDate()));
            preparedStatement.setString(7, "Visa");
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public List<Card> read() {
        List<Card> cards = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Cards";
            Statement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet == null) {
                statement.close();
                return cards;
            }
            if (resultSet.next()) {
                do {
                    String type = resultSet.getString("type");
                    Card card = cardFactory.createCard(type, resultSet);
                    card.setCardID(resultSet.getInt("cardID"));
                    cards.add(card);
                } while (resultSet.next());
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return cards;
    }

    public Card read(int cardID) {
        Card card = null;
        try {
            String sql = "SELECT * FROM Cards WHERE cardID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, cardID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String type = resultSet.getString("type");
                card = cardFactory.createCard(type, resultSet);
                card.setCardID(cardID);
            }
            resultSet.close();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        return card;
    }

    public void update(Card card, String type) {
        try {
            String sql = "UPDATE Cards SET cardNumber = ?, IBAN = ?, CVV = ?, PIN = ?, expiryDate = ?, type = ? WHERE cardID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setString(2, card.getIBAN());
            preparedStatement.setInt(3, card.getCVV());
            preparedStatement.setInt(4, card.getPIN());
            preparedStatement.setString(5, new SimpleDateFormat("yyyy-MM-dd").format(card.getExpiryDate()));
            preparedStatement.setString(6, type);
            preparedStatement.setInt(7, card.getCardID());
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void delete(Card card) {
        try {
            String sql = "DELETE FROM Cards WHERE cardID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, card.getCardID());
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }
}
