package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_NEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIRECTORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_ADD;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPdfDescriptor;
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
        return AddCommand.COMMAND_WORD + " " + getPdfDetails(pdf);
    }

    /**
     * Returns the part of command string for the given {@code pdf}'s details.
     */
    public static String getPdfDetails(Pdf pdf) {
        StringBuilder sb = new StringBuilder();
        sb.append("" + PREFIX_NAME + pdf.getName().getFullName())
                .append(" " + PREFIX_DIRECTORY + pdf.getDirectory().getDirectory())
                .append(" " + PREFIX_DEADLINE_NEW + pdf.getDeadline());

        pdf.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG_ADD + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPdfDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPdfDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.getFullName()).append(" "));
        //descriptor.getDirectory().ifPresent(dir -> sb.append(PREFIX_DIRECTORY)
        // .append(dir.getDirectory()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG_ADD);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG_ADD).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
