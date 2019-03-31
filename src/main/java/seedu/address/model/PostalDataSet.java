package seedu.address.model;

import java.util.HashMap;

/**
 * Represents the in-memory model of a list of postal codes.
 */
public class PostalDataSet {
    private HashMap<Integer, PostalData> postalDataHash = new HashMap<>();

    public void addData(PostalData data) {
        postalDataHash.put(data.getPostal(), data);
    }

    public PostalData getPostalData(int postal) {
        return postalDataHash.get(postal);
    }
}
