package seedu.address.storage;

import seedu.address.model.patient.Teeth;
import seedu.address.model.patient.exceptions.TeethLayoutException;
import seedu.address.model.tag.Tag;

/**
 * PDF-friendly version of {@link Tag}.
 */
class PdfAdaptedTeeth {

    private final int[] teethLayout;

    /**
     * Constructs a {@code PdfAdaptedTeeth} with the given {@code teethString}.
     */
    public PdfAdaptedTeeth(String teethString) {
        String[] sb = teethString.split(StorageConstants.DIVIDER);
        int[] parsedTeeth = new int[Teeth.PERMANENTTEETHCOUNT];

        if (sb.length == Teeth.PERMANENTTEETHCOUNT) {

            for (int i = 0; i < Teeth.PERMANENTTEETHCOUNT; i++) {
                parsedTeeth[i] = Integer.parseInt(sb[i]);
            }

            teethLayout = parsedTeeth;

        } else {
            throw new TeethLayoutException();
        }
    }

    /**
     * Converts a given {@code Teeth} into this class for PDF use.
     */
    public PdfAdaptedTeeth(Teeth source) {
        this.teethLayout = source.exportTeeth();
    }

    /**
     * Converts teeth int representation to a String representation.
     * @return the String representation.
     */
    public String getTeethName() {
        StringBuilder sb = new StringBuilder();
        sb.append(teethLayout[0]);

        for (int i = 1; i < teethLayout.length; i++) {
            sb.append(StorageConstants.DIVIDER + teethLayout[i]);
        }

        return sb.toString();
    }

    public int[] getTeeth() {
        return teethLayout;
    }
}
