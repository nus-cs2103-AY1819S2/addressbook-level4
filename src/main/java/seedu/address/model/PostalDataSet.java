package seedu.address.model;

import java.util.HashMap;
import java.util.Optional;

/**
 * Represents the in-memory model of a list of postal codes.
 */
public class PostalDataSet {
    private HashMap<String, PostalData> postalDataHash = new HashMap<>();

    public void addData(PostalData data) {
        postalDataHash.put(data.getPostal(), data);
    }

    public Optional<PostalData> getPostalData(String postal) {
        return Optional.ofNullable(postalDataHash.get(postal));
    }
}
