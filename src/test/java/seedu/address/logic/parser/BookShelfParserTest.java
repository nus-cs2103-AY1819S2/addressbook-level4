package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.SORT_AUTHOR_WITHOUT_PREFIX;
import static seedu.address.logic.commands.CommandTestUtil.SORT_AUTHOR_WITH_PREFIX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddBookCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteBookCommand;
import seedu.address.logic.commands.EditBookCommand;
import seedu.address.logic.commands.EditBookCommand.EditBookDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListBookCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SortBookCommand;
import seedu.address.logic.commands.SummaryCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookListFilterPredicate;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.BookUtil;
import seedu.address.testutil.EditBookDescriptorBuilder;

public class BookShelfParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final BookShelfParser parser = new BookShelfParser();

    @Test
    public void parseCommand_addBook() throws Exception {
        Book book = new BookBuilder().build();
        AddBookCommand command = (AddBookCommand) parser.parseCommand(BookUtil.getAddBookCommand(book));
        assertEquals(new AddBookCommand(book), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_deleteBook() throws Exception {
        DeleteBookCommand command = (DeleteBookCommand) parser.parseCommand(
                DeleteBookCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased());
        assertEquals(new DeleteBookCommand(INDEX_FIRST_BOOK), command);
    }

    @Test
    public void parseCommand_editBook() throws Exception {
        Book book = new BookBuilder().build();
        EditBookDescriptor descriptor = new EditBookDescriptorBuilder(book).build();
        EditBookCommand command = (EditBookCommand) parser.parseCommand(EditBookCommand.COMMAND_WORD + " "
                + INDEX_FIRST_BOOK.getOneBased() + " " + BookUtil.getEditBookDescriptorDetails(descriptor));
        assertEquals(new EditBookCommand(INDEX_FIRST_BOOK, descriptor), command);
    }

    @Test
    public void parseCommand_sortBook() throws Exception {
        List<String > sortTypes = new ArrayList<>();
        sortTypes.add(SORT_AUTHOR_WITHOUT_PREFIX);
        SortBookCommand command = (SortBookCommand) parser.parseCommand(
            SortBookCommand.COMMAND_WORD + SORT_AUTHOR_WITH_PREFIX);
        assertEquals(new SortBookCommand(sortTypes, null, new HashMap<>()), command);
    }

    @Test
    public void parseCommand_summary() throws Exception {
        assertTrue(parser.parseCommand(SummaryCommand.COMMAND_WORD) instanceof SummaryCommand);
        assertTrue(parser.parseCommand(SummaryCommand.COMMAND_WORD + " 3") instanceof SummaryCommand);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_listBook() throws Exception {
        Book book = new BookBuilder().build();
        ListBookCommand command = (ListBookCommand) parser.parseCommand(BookUtil.getListBookCommand(book));
        BookListFilterPredicate predicate = new BookListFilterPredicate(
                Arrays.asList(book.getBookName().fullName.split("\\s+")[0]),
                Arrays.asList(book.getAuthor().fullName.split("\\s+")[0]),
                book.getTags().stream().map(x -> x.tagName.split("\\s+")[0]).collect(Collectors.toList()),
                Arrays.asList(book.getRating().value)
            );
        assertEquals(new ListBookCommand(predicate), command);
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
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
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
