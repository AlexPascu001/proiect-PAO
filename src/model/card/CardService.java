package model.card;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class CardService {
    private Connection connection;
    private CardFactory cardFactory;
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
            System.out.println(e.toString());
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
            System.out.println(e.toString());
        }
    }

    public List<Card> read() {
        List<Card> cards = null;
        try {
            String sql = "SELECT * FROM Cards";
            Statement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            String type = resultSet.getString("type");
            while (resultSet.next()) {
                Card card = cardFactory.createCard(type, resultSet);
                cards.add(card);
            }
            resultSet.close();
            statement.close();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cards;
    }

    public void update(Card card) {
        try {
            String sql = "UPDATE Cards SET cardNumber = ?, IBAN = ?, CVV = ?, PIN = ?, expiryDate = ? WHERE cardID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, card.getCardNumber());
            preparedStatement.setString(2, card.getIBAN());
            preparedStatement.setInt(3, card.getCVV());
            preparedStatement.setInt(4, card.getPIN());
            preparedStatement.setString(5, new SimpleDateFormat("yyyy-MM-dd").format(card.getExpiryDate()));
            preparedStatement.setInt(6, card.getCardID());
            preparedStatement.execute();
            preparedStatement.close();
        }
        catch (SQLException e) {
            System.out.println(e.toString());
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
            System.out.println(e.toString());
        }
    }
}
