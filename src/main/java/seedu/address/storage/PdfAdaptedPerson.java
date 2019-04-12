package seedu.address.storage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.util.DrawTeethUtil;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.PersonIsNotPatient;
import seedu.address.model.person.Person;

/**
 * PDF-friendly version of {@link Person}.
 */
public class PdfAdaptedPerson implements PdfAdaptedInterface {

    private final String name;
    private final String sex;
    private final String nric;
    private final String dateOfBirth;
    private final String phone;
    private final String email;
    private final String address;
    private final String drugAllergy;
    private final String description;
    private final PdfAdaptedTeeth teeth;
    private final PdfAdaptedNextOfKin nextOfKin;
    private final List<PdfAdaptedTag> tagged = new ArrayList<>();
    private final List<PdfAdaptedRecord> records = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    public PdfAdaptedPerson(String name, String sex, String nric, String dateOfBirth, String phone, String email,
                            String address, String drugAllergy, String description, PdfAdaptedTeeth teeth,
                            PdfAdaptedNextOfKin nextOfKin, List<PdfAdaptedTag> tagged,
                            List<PdfAdaptedRecord> records) {
        this.name = name;
        this.sex = sex;
        this.nric = nric;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.teeth = teeth;
        this.drugAllergy = drugAllergy;
        this.description = description;
        this.nextOfKin = nextOfKin;

        if (tagged != null) {
            this.tagged.addAll(tagged);
        }

        if (records != null) {
            this.records.addAll(records);
        }
    }

    /**
     * Converts a given {@code Person} into this class for PDF use.
     */
    public PdfAdaptedPerson(Person source) {
        if (source instanceof Patient) {
            name = source.getName().fullName;
            sex = ((Patient) source).getSex().getSex();
            nric = ((Patient) source).getNric().getNric();
            dateOfBirth = ((Patient) source).getDateOfBirth().getRawFormat();
            phone = source.getPhone().value;
            email = source.getEmail().value;
            address = source.getAddress().value;
            drugAllergy = ((Patient) source).getDrugAllergy().toString();
            description = ((Patient) source).getPatientDesc().toString();
            teeth = new PdfAdaptedTeeth(((Patient) source).getTeeth());
            nextOfKin = new PdfAdaptedNextOfKin(((Patient) source).getNextOfKin());
            tagged.addAll(source.getTags().stream()
                    .map(PdfAdaptedTag::new)
                    .collect(Collectors.toList()));
            records.addAll(((Patient) source).getRecords().stream()
                    .map(PdfAdaptedRecord::new)
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
        ArrayList<String> stringArray = new ArrayList<>();
        stringArray.add("NRIC: " + nric);
        stringArray.add("Name: " + name);
        stringArray.add("Sex: " + sex);
        stringArray.add("Date of birth: " + dateOfBirth);
        stringArray.add("Home Address : " + address);
        stringArray.add("Phone Number: " + phone);
        stringArray.add("Email Address: " + email);
        stringArray.add("Drug Allergy: " + drugAllergy);
        stringArray.add("Description: " + description);
        stringArray.add("Teeth List: " + teeth.getTeethName());
        stringArray.add(getTags());
        addRecords(stringArray);
        stringArray.add("Next of kin Name: " + nextOfKin.getKinName());
        stringArray.add("Next of kin Relation: " + nextOfKin.getKinRelation());
        stringArray.add("Next of kin Phone: " + nextOfKin.getKinPhone());
        stringArray.add("Next of kin Address: " + nextOfKin.getKinAddress());
        stringArray.add("Next of kin Email: " + nextOfKin.getKinEmail());

        return stringArray;
    }

    /**
     * Creates a {@code String} for getStrings().
     * @return the tags of a PdfAdaptedPerson
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

        return sb.toString();
    }

    /**
     * Adds records to getStrings().
     */
    private void addRecords(ArrayList<String> stringArray) {
        stringArray.add("");
        stringArray.add("Records:");

        for (PdfAdaptedRecord record : records) {
            stringArray.add(record.getDoctorName());
            stringArray.add(record.getRecordDate());
            stringArray.add(record.getDescription());
            stringArray.add("");
        }
    }

    /**
     * Creates a {@code BufferedImage} for PDF export.
     * @return The teeth image of the person
     * @throws IOException if reading fails
     */
    public BufferedImage getTeethImage() throws IOException {
        try {
            return DrawTeethUtil.drawTeeth(teeth.getTeeth());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
