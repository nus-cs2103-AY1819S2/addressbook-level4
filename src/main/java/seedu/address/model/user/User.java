package seedu.address.model.user;

import java.util.List;

/**
 * Represents a user data and allows importing and exporting
 */
public class User {
    private String name;
    private List<CardData> cardData; // Contains User-specific data on Cards

    public void exportData() {
        //    TODO: Export data to CSV File depending on what information is passed to userClass
    }

    public void importData() {
        //    TODO: Export data to CSV File depending on what information is passed to userClass
    }

    public List<CardData> getCardData() {
        return cardData;
    }
}
