package seedu.address.model.patient;

import java.util.ArrayList;

import seedu.address.model.patient.exceptions.TeethLayoutException;

/**
 * Represents a set of teeth a Person has.
 */
public class Teeth {
    private static final String NONE = "none";
    private static final String CHILD = "child";
    private static final String ADULT = "adult";
    private static final int PRIMARYTEETHCOUNT = 20;
    private static final int PERMANENTTEETHCOUNT = 32;

    private ArrayList<Tooth> permanentTeeth;
    private ArrayList<Tooth> primaryTeeth;

    /**
     * Default constructor. Builds a teeth template according to the specified teeth layout.
     * @param teethLayout the teeth layout specified by the user.
     */
    Teeth(String teethLayout) {
        buildNoTeeth();
        switch (teethLayout) {
        case NONE:
            break;
        case CHILD:
            addPrimaryTeeth();
            break;
        case ADULT:
            addPermanentTeeth();
            break;
        default:
            throw new TeethLayoutException();
        }
    }

    /**
     * Initialises an empty set of primary and permanent teeth.
     */
    private void buildNoTeeth() {
        ArrayList<Tooth> primaryTeeth = new ArrayList<>();
        ArrayList<Tooth> permanentTeeth = new ArrayList<>();

        for (int i = 0; i < PRIMARYTEETHCOUNT; i++) {
            primaryTeeth.add(new Tooth(false));
        }

        this.primaryTeeth = primaryTeeth;

        for (int i = 0; i < PERMANENTTEETHCOUNT; i++) {
            permanentTeeth.add(new Tooth(false));
        }

        this.permanentTeeth = permanentTeeth;
    }

    /**
     * Initialises a set of good primary teeth.
     */
    private void addPrimaryTeeth() {
        for (Tooth tooth : primaryTeeth) {
            tooth.setPresent(true);
        }
    }

    /**
     * Initialises a set of good permanent teeth.
     */
    private void addPermanentTeeth() {
        for (Tooth tooth : permanentTeeth) {
            tooth.setPresent(true);
        }
    }

    /**
     * For primary tooth.
     * Returns a Tooth representing a patient's tooth using a tooth alphabet.
     * @param index the tooth alphabet of tooth to be retrieved.
     * @return the tooth represented by provided tooth alphabet.
     */
    public Tooth getTooth(char index) {
        int parsedIndex = (int) index - (int) 'a';
        return primaryTeeth.get(parsedIndex);
    }

    /**
     * For permanent tooth.
     * Returns a Tooth representing a patient's tooth using a tooth number.
     * @param index the tooth number of tooth to be retrieved.
     * @return the tooth represented by provided tooth number.
     */
    public Tooth getTooth(int index) {
        int adjustedIndex = index - 1;
        return permanentTeeth.get(adjustedIndex);
    }

}
