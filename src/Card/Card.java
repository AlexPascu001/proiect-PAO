package Card;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.function.Consumer;

public class Card {
    private final int cardID;
    private final int CVV;
    private final int PIN;
    private String cardNumber;
    private String IBAN;
    private Date expiryDate;

    public Card(int cardID, String IBAN) {
        this.cardID = cardID;
        this.CVV = generateCVV();
        this.PIN = generatePIN();
        this.cardNumber = generateCardNumber();
        this.IBAN = IBAN;
        this.expiryDate = generateExpiryDate();
    }

    public Card(int cardID, String IBAN, String name) {
        this.cardID = cardID;
        this.CVV = generateCVV();
        this.PIN = generatePIN();
        this.cardNumber = generateCardNumber();
        this.IBAN = IBAN;
        this.expiryDate = generateExpiryDate();
    }

    private int generateCVV() {
        return 100 + (int) (Math.random() * 900);
    }

    private int generatePIN() {
        return 1000 + (int) (Math.random() * 9000);
    }

    private String generateCardNumber() {
        char[] array = new char[16];
        var random = new Random();
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
                ", expiryDate=" + expiryDate +
                '}';
    }

    public String toCSV() {
        return cardID + "," + CVV + "," + PIN + "," + cardNumber + "," + IBAN + "," + expiryDate;
    }

    public int getCardID() {
        return cardID;
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
}
