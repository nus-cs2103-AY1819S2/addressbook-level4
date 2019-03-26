package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddDoctorCommand;
import seedu.address.model.person.Doctor;
import seedu.address.model.tag.Tag;

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
        sb.append(PREFIX_AGE + doctor.getAge().value + " ");
        doctor.getSpecs().stream().forEach(
            s -> sb.append(PREFIX_SPECIALISATION + s.specialisation + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditDoctorDescriptor}'s details.
     */

//    public static String getEditDoctorDescriptorDetails(EditPersonDescriptor descriptor) {
//        StringBuilder sb = new StringBuilder();
//        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
//        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
//        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
//        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
//        if (descriptor.getTags().isPresent()) {
//            Set<Tag> tags = descriptor.getTags().get();
//            if (tags.isEmpty()) {
//                sb.append(PREFIX_TAG);
//            } else {
//                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
//            }
//        }
//        return sb.toString();
//    }
}