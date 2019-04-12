package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_1_VALID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_NEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIRECTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_ADD;

import java.nio.file.Paths;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeadlineCommand;
import seedu.address.logic.commands.DecryptCommand;
import seedu.address.logic.commands.EditCommand.EditPdfDescriptor;
import seedu.address.logic.commands.EncryptCommand;
import seedu.address.model.pdf.Deadline;
import seedu.address.model.pdf.Pdf;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Pdf.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code pdf}.
     */
    public static String getAddCommand(Pdf pdf) {
        return AddCommand.COMMAND_WORD + " f/" + getPdfFilePath(pdf);
    }

    /**
     * Returns pdf file path for adding the {@code pdf}.
     */
    public static String getPdfFilePath(Pdf pdf) {
        return Paths.get(pdf.getDirectory().getDirectory(), pdf.getName().getFullName()).toString();
    }

    /**
     * Returns a deadline command string for setting deadline to the {@code pdf}.
     */
    public static String getDeadlineCommand(Pdf pdf, int index) {
        return DeadlineCommand.COMMAND_WORD + " " + index + " " + PREFIX_DEADLINE_NEW + getPdfDeadline(pdf);
    }

    /**
     * Returns pdf deadline for setting deadline the {@code pdf}.
     */
    public static String getPdfDeadline(Pdf pdf) {
        final String DEADLINE_SEPARATOR_PREFIX = "-";
        Deadline deadline = pdf.getDeadline();
        String[] splitDeadline = deadline.toJsonString().split(DEADLINE_SEPARATOR_PREFIX);
        return splitDeadline[2].substring(0, 2) + "-" + splitDeadline[1] + "-" + splitDeadline[0];
    }

    /**
     * Returns a decrypt command string for decrypting the pdf at {@code index}.
     */
    public static String getDecryptCommand(int index) {
        return DecryptCommand.COMMAND_WORD + " " + index + " " + PREFIX_PASSWORD + PASSWORD_1_VALID;
    }

    /**
     * Returns an encrypt command string for encrypting the pdf at {@code index}.
     */
    public static String getEncryptCommand(int index) {
        return EncryptCommand.COMMAND_WORD + " " + index + " " + PREFIX_PASSWORD + PASSWORD_1_VALID;
    }

    /**
     * Returns pdf password for decrypting the {@code pdf}.
     */
    public static String getDecryptPassword(Pdf pdf) {
        final String DEADLINE_SEPARATOR_PREFIX = "-";
        Deadline deadline = pdf.getDeadline();
        String[] splitDeadline = deadline.toJsonString().split(DEADLINE_SEPARATOR_PREFIX);
        return splitDeadline[2].substring(0, 2) + "-" + splitDeadline[1] + "-" + splitDeadline[0];
    }

    /**
     * Returns the part of command string for the given {@code EditPdfDescriptor}'s details.
     */
    public static String getRenamePdfDescriptorDetails(EditPdfDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.getFullName()));
        //descriptor.getDirectory().ifPresent(dir -> sb.append(PREFIX_DIRECTORY)
        // .append(dir.getDirectory()).append(" "));
        /*if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG_ADD);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG_ADD).append(s.tagName).append(" "));
            }
        }*/
        return sb.toString();
    }
}
