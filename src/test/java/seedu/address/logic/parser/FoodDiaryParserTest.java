package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddReviewCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditRestaurantDescriptor;
import seedu.address.logic.commands.EditReviewCommand;
import seedu.address.logic.commands.EditReviewCommand.EditReviewDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.NameContainsKeywordsPredicate;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.review.Review;
import seedu.address.testutil.EditRestaurantDescriptorBuilder;
import seedu.address.testutil.EditReviewDescriptorBuilder;
import seedu.address.testutil.RestaurantBuilder;
import seedu.address.testutil.RestaurantUtil;
import seedu.address.testutil.ReviewBuilder;
import seedu.address.testutil.ReviewUtil;

public class FoodDiaryParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final FoodDiaryParser parser = new FoodDiaryParser();

    @Test
    public void parseCommand_add() throws Exception {
        Restaurant restaurant = new RestaurantBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(RestaurantUtil.getAddCommand(restaurant));
        assertEquals(new AddCommand(restaurant), command);
    }

    @Test
    public void parseCommand_addReview() throws Exception {
        ReviewBuilder reviewBuilder = new ReviewBuilder();
        Review review = reviewBuilder.build();
        AddReviewCommand command = (AddReviewCommand) parser.parseCommand(ReviewUtil.getAddReviewCommand(review));
        Timestamp tsCommandReview = command.getReviewToAdd().getTimeStamp();
        //Ensures that both commands have reviews with the same timestamp, so that comparison can be done properly.
        reviewBuilder.withTimestamp(tsCommandReview);
        Review review2 = reviewBuilder.build();
        assertEquals(new AddReviewCommand(INDEX_FIRST_RESTAURANT, review2), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_RESTAURANT.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_RESTAURANT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Restaurant restaurant = new RestaurantBuilder().build();
        EditRestaurantDescriptor descriptor = new EditRestaurantDescriptorBuilder(restaurant).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_RESTAURANT.getOneBased() + " "
                + RestaurantUtil.getEditRestaurantDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_RESTAURANT, descriptor), command);
    }

    @Test
    public void parseCommand_editReview() throws Exception {
        Review review = new ReviewBuilder().build();
        EditReviewDescriptor descriptor = new EditReviewDescriptorBuilder(review).build();
        EditReviewCommand command = (EditReviewCommand) parser.parseCommand(EditReviewCommand.COMMAND_WORD
                + " "
                + INDEX_FIRST_RESTAURANT.getOneBased() + " "
                + ReviewUtil.getEditReviewDescriptorDetails(descriptor));
        assertEquals(new EditReviewCommand(INDEX_FIRST_RESTAURANT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
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
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_RESTAURANT.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_RESTAURANT), command);
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
