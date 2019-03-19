package seedu.address.model;

import java.util.HashSet;

/**
 * Represents the in-memory model of a list of postal codes.
 */
public class PostalDataSet {
    private HashSet<PostalData> postalDataHash = new HashSet<>();

    public void addData(PostalData data) {
        postalDataHash.add(data);
    }
}
