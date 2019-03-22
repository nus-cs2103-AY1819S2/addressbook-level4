package seedu.travel.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.travel.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.travel.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.travel.testutil.TypicalIndexes.INDEX_FIRST_PLACE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.travel.logic.commands.AddCommand;
import seedu.travel.logic.commands.ClearCommand;
import seedu.travel.logic.commands.DeleteCommand;
import seedu.travel.logic.commands.EditCommand;
import seedu.travel.logic.commands.EditCommand.EditPlaceDescriptor;
import seedu.travel.logic.commands.ExitCommand;
import seedu.travel.logic.commands.HelpCommand;
import seedu.travel.logic.commands.HistoryCommand;
import seedu.travel.logic.commands.ListCommand;
import seedu.travel.logic.commands.RedoCommand;
import seedu.travel.logic.commands.SearchCommand;
import seedu.travel.logic.commands.SearchRatingCommand;
import seedu.travel.logic.commands.SearchTagsCommand;
import seedu.travel.logic.commands.SelectCommand;
import seedu.travel.logic.commands.UndoCommand;
import seedu.travel.logic.parser.exceptions.ParseException;
import seedu.travel.model.place.NameContainsKeywordsPredicate;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.RatingContainsKeywordsPredicate;
import seedu.travel.model.place.TagContainsKeywordsPredicate;
import seedu.travel.testutil.EditPlaceDescriptorBuilder;
import seedu.travel.testutil.PlaceBuilder;
import seedu.travel.testutil.PlaceUtil;

public class TravelBuddyParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TravelBuddyParser parser = new TravelBuddyParser();

    @Test
    public void parseCommand_add() throws Exception {
        Place place = new PlaceBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PlaceUtil.getAddCommand(place));
        assertEquals(new AddCommand(place), command);
    }

    @Test
    public void parseCommand_addAlias() throws Exception {
        Place place = new PlaceBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(AddCommand.COMMAND_ALIAS
                + " " + PlaceUtil.getPlaceDetails(place));
        assertEquals(new AddCommand(place), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_clearAlias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PLACE.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PLACE), command);
    }

    @Test
    public void parseCommand_deleteAlias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PLACE.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PLACE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Place place = new PlaceBuilder().build();
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder(place).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PLACE.getOneBased() + " " + PlaceUtil.getEditPlaceDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PLACE, descriptor), command);
    }

    @Test
    public void parseCommand_editAlias() throws Exception {
        Place place = new PlaceBuilder().build();
        EditPlaceDescriptor descriptor = new EditPlaceDescriptorBuilder(place).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PLACE.getOneBased() + " " + PlaceUtil.getEditPlaceDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PLACE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_search() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        SearchCommand command = (SearchCommand) parser.parseCommand(SearchCommand.COMMAND_WORD
                + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_searchAlias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        SearchCommand command = (SearchCommand) parser.parseCommand(SearchCommand.COMMAND_ALIAS
                + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_searchRating() throws Exception {
        List<String> keywords = Arrays.asList("1", "4", "5");
        SearchRatingCommand command = (SearchRatingCommand) parser.parseCommand(
                SearchRatingCommand.COMMAND_WORD + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchRatingCommand(new RatingContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_searchTags() throws Exception {
        List<String> keywords = Arrays.asList("school", "temple", "airport");
        SearchTagsCommand command = (SearchTagsCommand) parser.parseCommand(SearchTagsCommand.COMMAND_WORD
                + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new SearchTagsCommand(new TagContainsKeywordsPredicate(keywords)), command);
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
    public void parseCommand_historyAlias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);

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
    public void parseCommand_listAlias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PLACE.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PLACE), command);
    }

    @Test
    public void parseCommand_selectAlias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PLACE.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PLACE), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
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
