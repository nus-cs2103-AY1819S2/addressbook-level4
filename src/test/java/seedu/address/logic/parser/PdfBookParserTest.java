package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.DIR_1_VALID;
import static seedu.address.logic.commands.CommandTestUtil.NAME_2_VALID;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_1_VALID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIRECTORY;
import static seedu.address.model.Model.COMPARATOR_DEADLINE_ASCENDING_PDFS;
import static seedu.address.model.Model.COMPARATOR_DEADLINE_DESCENDING_PDFS;
import static seedu.address.model.Model.COMPARATOR_NAME_ASCENDING_PDFS;
import static seedu.address.model.Model.COMPARATOR_NAME_DESCENDING_PDFS;
import static seedu.address.model.Model.COMPARATOR_SIZE_ASCENDING_PDFS;
import static seedu.address.model.Model.COMPARATOR_SIZE_DESCENDING_PDFS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PDF;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PDF;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PDF;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_1;
import static seedu.address.testutil.TypicalPdfs.SAMPLE_PDF_2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeadlineCommand;
import seedu.address.logic.commands.DecryptCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EncryptCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MergeCommand;
import seedu.address.logic.commands.MoveCommand;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.commands.RenameCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.pdf.NameContainsKeywordsPredicate;
import seedu.address.model.pdf.Pdf;
import seedu.address.model.pdf.TagContainsKeywordsPredicate;
import seedu.address.testutil.EditPdfDescriptorBuilder;
import seedu.address.testutil.PdfBuilder;
import seedu.address.testutil.PdfUtil;

public class PdfBookParserTest {

    private static final String DEADLINE_NEWLY_ADDED_FILE = "NEWLY ADDED";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final PdfBookParser parser = new PdfBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Pdf pdf = new PdfBuilder(SAMPLE_PDF_1).withDeadline(DEADLINE_NEWLY_ADDED_FILE).build();
        AddCommand command = (AddCommand) parser.parseCommand(PdfUtil.getAddCommand(pdf));
        assertEquals(new AddCommand(pdf), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deadline() throws Exception {
        Pdf pdf = SAMPLE_PDF_2;
        DeadlineCommand command = (DeadlineCommand) parser.parseCommand(PdfUtil.getDeadlineCommand(pdf, 2));
        assertEquals(new DeadlineCommand(INDEX_SECOND_PDF, pdf.getDeadline(),
                DeadlineCommand.DeadlineAction.NEW), command);
    }

    @Test
    public void parseCommand_decrypt() throws Exception {
        DecryptCommand command = (DecryptCommand) parser.parseCommand(PdfUtil.getDecryptCommand(1));
        assertEquals(new DecryptCommand(INDEX_FIRST_PDF, PASSWORD_1_VALID), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PDF.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PDF), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Pdf pdf = new PdfBuilder(SAMPLE_PDF_1).withName(NAME_2_VALID).build();
        RenameCommand.EditPdfDescriptor descriptor = new EditPdfDescriptorBuilder()
                .withName(pdf.getName().getFullName()).build();

        RenameCommand command = (RenameCommand) parser.parseCommand(RenameCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PDF.getOneBased() + " " + PdfUtil.getRenamePdfDescriptorDetails(descriptor));
        assertEquals(new RenameCommand(INDEX_FIRST_PDF, descriptor), command);
    }

    @Test
    public void parseCommand_encrypt() throws Exception {
        EncryptCommand command = (EncryptCommand) parser.parseCommand(PdfUtil.getEncryptCommand(1));
        assertEquals(new EncryptCommand(INDEX_FIRST_PDF, PASSWORD_1_VALID), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        FilterCommand command = (FilterCommand) parser.parseCommand(
                PdfUtil.getFilterCommand(SAMPLE_PDF_2.getTags()));
        assertEquals(new FilterCommand(new TagContainsKeywordsPredicate(
                Arrays.asList("w9", "CS2103T", "lecture"))), command);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_open() throws Exception {
        OpenCommand command = (OpenCommand) parser.parseCommand(OpenCommand.COMMAND_WORD + " 1");
        assertEquals(new OpenCommand(INDEX_FIRST_PDF), command);
    }

    @Test
    public void parseCommand_move() throws Exception {
        MoveCommand command = (MoveCommand) parser.parseCommand(MoveCommand.COMMAND_WORD + " 1 "
                + PREFIX_DIRECTORY + DIR_1_VALID);
        assertEquals(new MoveCommand(INDEX_FIRST_PDF, SAMPLE_PDF_1.getDirectory()), command);
    }

    @Test
    public void parseCommand_tag() throws Exception {
        TagCommand command = (TagCommand) parser.parseCommand(TagCommand.COMMAND_WORD + " 1 "
                + PdfUtil.getAddTag(SAMPLE_PDF_2.getTags()));
        assertEquals(new TagCommand(INDEX_FIRST_PDF, SAMPLE_PDF_2.getTags(), true), command);

        command = (TagCommand) parser.parseCommand(TagCommand.COMMAND_WORD + " 1 "
                + PdfUtil.getRemoveTag(SAMPLE_PDF_2.getTags()));
        assertEquals(new TagCommand(INDEX_FIRST_PDF, SAMPLE_PDF_2.getTags(), false), command);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        SortCommand command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " name up");
        assertEquals(new SortCommand(COMPARATOR_NAME_ASCENDING_PDFS), command);

        command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " name down");
        assertEquals(new SortCommand(COMPARATOR_NAME_DESCENDING_PDFS), command);

        command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " deadline up");
        assertEquals(new SortCommand(COMPARATOR_DEADLINE_ASCENDING_PDFS), command);

        command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " deadline down");
        assertEquals(new SortCommand(COMPARATOR_DEADLINE_DESCENDING_PDFS), command);

        command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " size up");
        assertEquals(new SortCommand(COMPARATOR_SIZE_ASCENDING_PDFS), command);

        command = (SortCommand) parser.parseCommand(
                SortCommand.COMMAND_WORD + " size down");
        assertEquals(new SortCommand(COMPARATOR_SIZE_DESCENDING_PDFS), command);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PDF.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PDF), command);
    }

    @Test
    public void parseCommand_merge() throws Exception {
        MergeCommand command = (MergeCommand) parser.parseCommand(MergeCommand.COMMAND_WORD + " " + "1 2 3");
        assertEquals(new MergeCommand(INDEX_FIRST_PDF, INDEX_SECOND_PDF, INDEX_THIRD_PDF), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
