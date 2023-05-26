package model.card;


import java.sql.ResultSet;
import java.text.ParseException;
import java.util.*;

public abstract class Card {
    private int cardID;
    private int CVV;
    private int PIN;
    private String cardNumber;
    private String IBAN;
    private Date expiryDate;

    private static final Set<String> cardNumbers = new HashSet<>();
    private static final Random random = new Random();

    public Card(int cardID, String IBAN) {
        this.cardID = cardID;
        this.CVV = generateCVV();
        this.PIN = generatePIN();
        this.cardNumber = generateCardNumber();

        while (cardNumbers.contains(this.cardNumber)) {
            this.cardNumber = generateCardNumber();
        }
        cardNumbers.add(this.cardNumber);

        this.IBAN = IBAN;
        this.expiryDate = generateExpiryDate();
    }

    public Card(int cardID, ResultSet in) {
        this.cardID = cardID;
        this.read(in);
    }

    public Card(int cardID, Scanner in) throws ParseException {
        this.cardID = cardID;
        this.read(in);
    }

    public void read(ResultSet in) {
        try {
            this.cardNumber = in.getString("cardNumber");
            this.CVV = in.getInt("CVV");
            this.PIN = in.getInt("PIN");
            this.IBAN = in.getString("IBAN");
            this.expiryDate = in.getDate("expiryDate");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void read(Scanner in) {
        System.out.print("IBAN: ");
        this.IBAN = in.nextLine();
        this.expiryDate = generateExpiryDate();
        this.CVV = generateCVV();
        this.PIN = generatePIN();
        this.cardNumber = generateCardNumber();
    }

    private int generateCVV() {
        return 100 + random.nextInt(900);
    }

    private int generatePIN() {
        return 1000 + random.nextInt(9000);
    }

    private String generateCardNumber() {
        char[] array = new char[16];
        for (int i = 0; i < 16; i++) {
            array[i] = (char) (48 + (Math.abs(random.nextInt()) % 10));
        }
        return new String(array);
    }

    private Date generateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 5);
        return calendar.getTime();
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardID=" + cardID +
                ", CVV=" + CVV +
                ", PIN=" + PIN +
                ", cardNumber='" + cardNumber + '\'' +
                ", IBAN='" + IBAN + '\'' +
                ", expiryDate=" + expiryDate;
    }

    public String toCSV() {
        return "Card," + cardID + "," + CVV + "," + PIN + "," + cardNumber + "," + IBAN + "," + expiryDate;
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public int getCVV() {
        return CVV;
    }

    public int getPIN() {
        return PIN;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getIBAN() {
        return IBAN;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public abstract double fee();

    @Override
    public int hashCode() {
        return Objects.hash(cardID, CVV, PIN, cardNumber, IBAN, expiryDate);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Card && ((Card) obj).cardID == this.cardID;
    }
}
