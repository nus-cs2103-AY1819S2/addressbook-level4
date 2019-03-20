package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Teeth;
import seedu.address.model.patient.exceptions.TeethLayoutException;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Tag}.
 */
class JsonAdaptedTeeth {

    private final int[] teethLayout;

    /**
     * Constructs a {@code JsonAdaptedTeeth} with the given {@code teethString}.
     */
    @JsonCreator
    public JsonAdaptedTeeth(String teethString) {
        String[] sb = teethString.split(JsonAdaptedConstants.DIVIDER);
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
     * Converts a given {@code Teeth} into this class for Jackson use.
     */
    public JsonAdaptedTeeth(Teeth source) {
        this.teethLayout = source.exportTeeth();
    }

    /**
     * Converts teeth int representation to a String representation.
     * @return the String representation.
     */
    @JsonValue
    public String getTeethName() {
        StringBuilder sb = new StringBuilder(teethLayout[0]);

        for (int i = 1; i < teethLayout.length; i++) {
            sb.append(JsonAdaptedConstants.DIVIDER + teethLayout[i]);
        }

        return sb.toString();
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Teeth toModelType() throws IllegalValueException {
        if (Teeth.isValidTeeth(teethLayout)) {
            return new Teeth(teethLayout);
        } else {
            throw new TeethLayoutException();
        }
    }

}
