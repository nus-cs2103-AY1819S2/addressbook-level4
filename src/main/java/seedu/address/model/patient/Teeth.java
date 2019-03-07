package seedu.address.model.patient;

import java.util.ArrayList;

import seedu.address.model.patient.exceptions.TeethLayoutException;

/**
 * Represents a set of teeth a Person has.
 */
public class Teeth {
    private static final String NONE = "none";
    private static final String CHILD = "child";
    private static final int PRIMARYTEETHCOUNT = 20;
    private static final String ADULT = "adult";
    private static final int PERMANENTTEETHCOUNT = 32;

    private ArrayList<Tooth> permanentTeeth;
    private ArrayList<Tooth> primaryTeeth;

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
     * Initialises a set of good primary teeth.
     */
    private void buildPrimaryTeeth() {
        ArrayList<Tooth> primaryTeeth = new ArrayList<>();
        for (int i = 0; i < PRIMARYTEETHCOUNT; i++) {
            primaryTeeth.add(new Tooth());
        }
        this.primaryTeeth = primaryTeeth;
    }

    /**
     * Initialises a set of good permanent teeth.
     */
    private void buildPermanentTeeth() {
        ArrayList<Tooth> permanentTeeth = new ArrayList<>();
        for (int i = 0; i < PERMANENTTEETHCOUNT; i++) {
            permanentTeeth.add(new Tooth());
        }
        this.permanentTeeth = permanentTeeth;
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
