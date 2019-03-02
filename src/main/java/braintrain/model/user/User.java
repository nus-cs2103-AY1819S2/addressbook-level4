package braintrain.model.user;

import java.util.List;

/**
 * Represents a user data and allows importing and exporting
 */
public class User {
    private String name;
    private List<CardData> Carddata; // Contains User-specific data on Cards

    /**
     * TODO: Export data to CSV File depending on what information is passed to userClass
     */
    public void exportData() {
    }

    /**
     * TODO :import data from CSV File depending on what information is passed to userClass
     */
    public void importData() {
    }

    public List<CardData> getCarddata() {
        return Carddata;
    }
}
