package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.PersonIsNotPatient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class PdfAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private int index;
    private final String name;
    private final String nric;
    private final String dateOfBirth;
    private final String phone;
    private final String email;
    private final String address;
    private final List<PdfAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    public PdfAdaptedPerson(String name, String nric, String dateOfBirth, String phone,
                            String email, String address, List<PdfAdaptedTag> tagged) {
        this.name = name;
        this.nric = nric;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for PDF use.
     */
    public PdfAdaptedPerson(Person source) {
        if (source instanceof Patient) {
            name = source.getName().fullName;
            nric = ((Patient) source).getNric().getNric();
            dateOfBirth = ((Patient) source).getDateOfBirth().getRawFormat();
            phone = source.getPhone().value;
            email = source.getEmail().value;
            address = source.getAddress().value;
            tagged.addAll(source.getTags().stream()
                    .map(PdfAdaptedTag::new)
                    .collect(Collectors.toList()));
        } else {
            throw new PersonIsNotPatient();
        }
    }

    /**
     * Sets the index of a PdfAdaptedPerson for exporting.
     */
    public void setIndex(int index) {
        this.index = index;
    }

}
