package seedu.address.model.patient;

import java.util.ArrayList;

import seedu.address.model.patient.exceptions.TeethLayoutException;
import seedu.address.storage.JsonAdaptedConstants;

/**
 * Represents a set of teeth a Person has.
 */
public class Teeth implements ExportableTeeth {
    public static final int ABSENT = 2;
    public static final int PROBLEMATIC = 1;
    public static final int HEALTHY = 0;
    public static final int PERMANENTTEETHCOUNT = 32;
    public static final String ADULT = "adult";

    private String teethLayout;
    private ArrayList<Tooth> permanentTeeth = new ArrayList<>();

    /**
     * Default constructor. Builds an adult teeth layout.
     */
    public Teeth() {
        initialisePermanentTeeth();
        setPermanentTeeth();
    }

    /**
     * Creates a teeth based on stored information.
     * @param teethLayout the stored teeth information.
     */
    public Teeth(int[] teethLayout) {
        initialisePermanentTeeth();
        importPermanentTeeth(teethLayout);
    }

    /**
     * Initialises teeth in the teeth array.
     */
    private void initialisePermanentTeeth() {
        this.teethLayout = ADULT;
        for (int i = 0; i < PERMANENTTEETHCOUNT; i++) {
            permanentTeeth.add(new Tooth());
        }
    }

    /**
     * Sets the permanent teeth according to an int array.
     * @param teethLayout the layout information.
     */
    public void importPermanentTeeth(int[] teethLayout) {
        for (int i = 0; i < permanentTeeth.size(); i++) {
            Tooth tooth = permanentTeeth.get(i);
            int status = teethLayout[i];

            if (status == ABSENT) {
                tooth.setAbsent();
            } else if (status == PROBLEMATIC) {
                tooth.setStatus();
            } else if (status == HEALTHY) {
                tooth.setPresent();
            } else {
                throw new TeethLayoutException();
            }
        }
    }

    /**
     * Initialises a set of good permanent teeth.
     */
    private void setPermanentTeeth() {
        this.teethLayout = ADULT;
        for (Tooth tooth : permanentTeeth) {
            tooth.setPresent();
        }
    }

    /**
     * Checks if the teeth int array is valid.
     * @param teethLine the int array representing teeth.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidTeeth(int[] teethLine) {
        for (int i : teethLine) {
            if (i == 0 || i == 1 || i == 2) {
                return false;
            }
        }
        return true;
    }

    /**
     * For permanent tooth.
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
     * Checks if the tooth number is valid.
     * @param toothNumber the tooth number to be checked.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidTooth(int toothNumber) {
        return toothNumber > 0 && toothNumber < 33;
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
     * 2 - Absent. (black).
     * 1 - Problematic. (red).
     * 0 - Present, healthy. (white).
     * @return an Integer array consisting of current teeth layout information.
     */
    @Override
    public int[] exportTeeth() {
        int[] exportFormat = new int[32];

        for (int i = 0; i < permanentTeeth.size(); i++ ) {
            Tooth tooth = permanentTeeth.get(i);
            if (!tooth.isPresent()) {
                exportFormat[i] = ABSENT;
            } else if (tooth.isOnStatus()) {
                exportFormat[i] = PROBLEMATIC;
            } else {
                exportFormat[i] = HEALTHY;
            }
        }

        return exportFormat;
    }

    /**
     * Get the raw format of the teeth structure to be stored.
     * @return the raw format in a String.
     */
    public String getRawFormat() {
        int[] teethLayout = exportTeeth();

        StringBuilder sb = new StringBuilder(teethLayout[0]);

        for (int i = 1; i < teethLayout.length; i++) {
            sb.append(JsonAdaptedConstants.DIVIDER + teethLayout[i]);
        }

        return sb.toString();
    }
}
