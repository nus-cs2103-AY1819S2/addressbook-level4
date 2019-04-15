package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.logic.commands.PatientAddCommand;
import seedu.address.logic.commands.PatientEditCommand.EditPersonDescriptor;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.PersonIsNotPatient;
import seedu.address.model.person.Person;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return PatientAddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        if (person instanceof Patient) {
            StringBuilder sb = new StringBuilder();
            sb.append(PREFIX_NAME + person.getName().fullName + " ");
            sb.append(PREFIX_SEX + ((Patient) person).getSex().getSex() + " ");
            sb.append(PREFIX_NRIC + ((Patient) person).getNric().getNric() + " ");
            sb.append(PREFIX_YEAR + ((Patient) person).getDateOfBirth().getRawFormat() + " ");
            sb.append(PREFIX_PHONE + person.getPhone().value + " ");
            sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
            sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
            return sb.toString();
        } else {
            throw new PersonIsNotPatient();
        }
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        return sb.toString();
    }
}
