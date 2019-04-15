package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Set;

import seedu.address.logic.commands.doctor.AddDoctorCommand;
import seedu.address.logic.commands.doctor.EditDoctorCommand.EditDoctorDescriptor;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.specialisation.Specialisation;

/**
 * A utility class for Doctor.
 */

public class DoctorUtil {

    /**
     * Returns an add command string for adding the {@code doctor}.
     */

    public static String getAddDoctorCommand(Doctor doctor) {
        return AddDoctorCommand.COMMAND_WORD + " " + getDoctorDetails(doctor);
    }

    /**
     * Returns the part of command string for the given {@code doctor}'s details.
     */

    public static String getDoctorDetails(Doctor doctor) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + doctor.getName().fullName + " ");
        sb.append(PREFIX_PHONE + doctor.getPhone().value + " ");
        sb.append(PREFIX_GENDER + doctor.getGender().value + " ");
        sb.append(PREFIX_YEAR + doctor.getYear().value + " ");
        doctor.getSpecs().stream().forEach(
            s -> sb.append(PREFIX_SPECIALISATION + s.specialisation + " ")
        );
        return sb.toString();
    }


    /**
     * Returns the part of command string for the given {@code EditDoctorDescriptor}'s details.
     */
    public static String getEditDoctorDescriptorDetails(EditDoctorDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(PREFIX_GENDER).append(gender.value).append(" "));
        descriptor.getYear().ifPresent(year -> sb.append(PREFIX_YEAR).append(year.value).append(" "));
        if (descriptor.getSpecs().isPresent()) {
            Set<Specialisation> specs = descriptor.getSpecs().get();
            if (specs.isEmpty()) {
                sb.append(PREFIX_SPECIALISATION);
            } else {
                specs.forEach(s -> sb.append(PREFIX_SPECIALISATION).append(s.specialisation).append(" "));
            }
        }
        return sb.toString();
    }
}
