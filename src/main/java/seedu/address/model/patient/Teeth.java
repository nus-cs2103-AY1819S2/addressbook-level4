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
    private String teethLayout;

    private ArrayList<Tooth> permanentTeeth;
    private ArrayList<Tooth> primaryTeeth;

    /**
     * Default constructor. Builds a teeth template according to the specified teeth layout.
     * @param teethLayout the teeth layout specified by the user.
     */
    Teeth(String teethLayout) {
        this.teethLayout = teethLayout;

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
     * For permanent tooth.
     * Sets a status for a tooth.
     * @param index the tooth alphabet.
     * @param status the status of the tooth.
     */
    public void setToothStatus(char index, Status status) {
        getTooth(index).setStatus(status);
    }

    /**
     * For primary tooth.
     * Sets a status for a tooth.
     * @param index the tooth number.
     * @param status the status of the tooth.
     */
    public void setToothStatus(int index, Status status) {
        getTooth(index).setStatus(status);
    }

    /**
     * Returns the type of teeth the patient has.
     * Either primary or permanent.
     * @return the teeth type.
     */
    public String getTeethType() {
        return teethLayout;
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

    /**
     * Exports the format of the teeth. (For permanent teeth).
     * Used mainly to print the preview of the teeth layout on GUI to user.
     * 0 - Absent. (Red).
     * 1 - Present, on status. (Yellow).
     * 2 - Present, healthy. (White).
     * @return an Integer array consisting of current teeth layout information.
     */
    public ArrayList<Integer> exportTeeth() {
        ArrayList<Integer> exportFormat = new ArrayList<>();

        for (Tooth tooth : permanentTeeth) {
            if (!tooth.isPresent()) {
                exportFormat.add(0);
            } else if (tooth.isOnStatus()) {
                exportFormat.add(1);
            } else {
                exportFormat.add(2);
            }
        }

        return exportFormat;
    }
}
