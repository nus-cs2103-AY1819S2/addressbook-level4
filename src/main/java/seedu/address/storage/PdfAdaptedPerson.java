package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.PersonIsNotPatient;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Person}.
 */
class PdfAdaptedPerson implements PdfAdaptedInterface {

    public static final int ATTRIBUTES = 7;

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
     * Creates a {@code String[]} for PDF exporting.
     * @return the attributes of a PdfAdaptedPerson
     */
    @Override
    public ArrayList<String> getStrings() {
        ArrayList<String> stringArray = new ArrayList<>(ATTRIBUTES);
        stringArray.add("NRIC: " + nric);
        stringArray.add("Name: " + name);
        stringArray.add("Date of birth: " + dateOfBirth);
        stringArray.add("Home Address : " + address);
        stringArray.add("Phone Number: " + phone);
        stringArray.add("Email Address: " + email);
        stringArray.add(getTags());

        return stringArray;
    }

    /**
     * Creates a {@code String} for getStrings().
     * @return the tags a PdfAdaptedPerson
     */
    private String getTags() {
        String message = "Tags: [";

        StringBuilder sb = new StringBuilder(message);

        for (PdfAdaptedTag tag : tagged) {
            sb.append(tag.getTagName());
            sb.append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());
        sb.append("]");

        System.out.println(sb);

        return sb.toString();
    }
}
