package braintrain.model.user;

import java.util.List;

/**
 * Represents a user data and allows importing and exporting
 */
public class User {
    private String name;
    private List<CardData> Carddata; // Contains User-specific data on Cards

    public void exportData() {

    } // Export to CSV file what this UserData object stores

    public void importData() {

    } //import from csv into this user data object

    public List<CardData> getCarddata() {
        return Carddata;
    }
}
