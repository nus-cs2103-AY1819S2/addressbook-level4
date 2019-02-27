package seedu.address.model.person;

import java.util.ArrayList;

/**
 * Represents a set of teeth a Person has.
 */
public class Teeth {
    private final int childTeethCount = 20;
    private final int adultTeethCount = 32;

    private ArrayList<Tooth> teeth;

    public Teeth() {
        teeth = new ArrayList<>();
    }

    /**
     * Returns a Tooth representing a patient's tooth using a tooth number.
     * @param toothNum the tooth number of tooth to be retrieved.
     * @return the tooth represented by provided tooth number.
     */
    public Tooth getTooth(int toothNum) {
        return null;
    }
}
