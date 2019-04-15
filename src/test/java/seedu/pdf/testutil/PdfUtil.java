package seedu.pdf.testutil;

import static seedu.pdf.logic.commands.CommandTestUtil.PASSWORD_1_VALID;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_DEADLINE_NEW;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_TAG_ADD;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_TAG_NAME;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_TAG_REMOVE;

import java.nio.file.Paths;
import java.util.Set;

import seedu.pdf.logic.commands.AddCommand;
import seedu.pdf.logic.commands.DeadlineCommand;
import seedu.pdf.logic.commands.DecryptCommand;
import seedu.pdf.logic.commands.EncryptCommand;
import seedu.pdf.logic.commands.FilterCommand;
import seedu.pdf.logic.commands.RenameCommand.EditPdfDescriptor;
import seedu.pdf.model.pdf.Deadline;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.model.tag.Tag;

/**
 * A utility class for Pdf.
 */
public class PdfUtil {

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
        final String deadlineSeparatorPrefix = "-";
        Deadline deadline = pdf.getDeadline();
        String[] splitDeadline = deadline.toJsonString().split(deadlineSeparatorPrefix);
        return splitDeadline[2].substring(0, 2) + deadlineSeparatorPrefix
                + splitDeadline[1] + deadlineSeparatorPrefix + splitDeadline[0];
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
     * Returns a filter command string for returning a filtered list with that matches with {code tags}.
     */
    public static String getFilterCommand(Set<Tag> tags) {
        StringBuilder sb = new StringBuilder();
        sb.append(FilterCommand.COMMAND_WORD);
        tags.stream().forEach(tag -> sb.append(" ").append(PREFIX_TAG_NAME).append(tag.tagName));

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPdfDescriptor}'s details.
     */
    public static String getRenamePdfDescriptorDetails(EditPdfDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.getFullName()));
        return sb.toString();
    }

    /**
     * Returns the add tag command with {@code tags}.
     */
    public static String getAddTag(Set<Tag> tags) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TAG_ADD).append(" ");
        tags.stream().forEach(x -> sb.append(PREFIX_TAG_NAME).append(x.tagName).append(" "));

        return sb.toString();
    }

    /**
     * Returns the add tag command with {@code tags}.
     */
    public static String getRemoveTag(Set<Tag> tags) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_TAG_REMOVE).append(" ");
        tags.stream().forEach(x -> sb.append(PREFIX_TAG_NAME).append(x.tagName).append(" "));

        return sb.toString();
    }
}
