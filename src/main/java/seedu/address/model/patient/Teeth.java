package seedu.address.model.patient;

import java.util.ArrayList;

import seedu.address.model.patient.exceptions.TeethLayoutException;

/**
 * Represents a set of teeth a Person has.
 */
public class Teeth {
    private static final String NONE = "none";
    private static final String CHILD = "child";
    private static final int CHILDTEETHCOUNT = 20;
    private static final String ADULT = "adult";
    private static final int ADULTTEETHCOUNT = 32;

    private ArrayList<Tooth> permanentTeeth = new ArrayList<>();
    private ArrayList<Tooth> primaryTeeth = new ArrayList<>();

    Teeth(String teethLayout) {
        switch (teethLayout) {
        case NONE:
            break;
        case CHILD:
            break;
        case ADULT:
            break;
        default:
            throw new TeethLayoutException();
        }
    }

    /**
     * Returns a Tooth representing a patient's tooth using a tooth number.
     * @param index the tooth number of tooth to be retrieved.
     * @return the tooth represented by provided tooth number.
     */
    public Tooth getTooth(int index) {
        return null;
    }
}
