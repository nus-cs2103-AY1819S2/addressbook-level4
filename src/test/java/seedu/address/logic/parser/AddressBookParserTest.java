package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.PatientAddCommand;
import seedu.address.logic.commands.PatientClearCommand;
import seedu.address.logic.commands.PatientDeleteCommand;
import seedu.address.logic.commands.PatientEditCommand;
import seedu.address.logic.commands.PatientEditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.PatientFindCommand;
import seedu.address.logic.commands.PatientListCommand;
import seedu.address.logic.commands.PatientSelectCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.util.predicate.ContainsKeywordsPredicate;
import seedu.address.model.util.predicate.MultipleContainsKeywordsPredicate;
import seedu.address.model.util.predicate.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        PatientAddCommand command = (PatientAddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new PatientAddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(PatientClearCommand.COMMAND_WORD) instanceof PatientClearCommand);
        assertTrue(parser.parseCommand(PatientClearCommand.COMMAND_WORD + " 3") instanceof PatientClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        PatientDeleteCommand command = (PatientDeleteCommand) parser.parseCommand(
                PatientDeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new PatientDeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        PatientEditCommand command = (PatientEditCommand) parser.parseCommand(PatientEditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new PatientEditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        PatientFindCommand command = (PatientFindCommand) parser.parseCommand(
                PatientFindCommand.COMMAND_WORD + " n/" + keywords.stream().collect(Collectors.joining(" ")));
        MultipleContainsKeywordsPredicate tempPred = new MultipleContainsKeywordsPredicate(Collections.emptyList(),
            true, false);
        List<ContainsKeywordsPredicate> predList = new ArrayList<ContainsKeywordsPredicate>();
        predList.add(new NameContainsKeywordsPredicate(keywords));
        tempPred.setPredicateList(predList);
        assertEquals(new PatientFindCommand(tempPred), command);
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
        assertTrue(parser.parseCommand(PatientListCommand.COMMAND_WORD) instanceof PatientListCommand);
        assertTrue(parser.parseCommand(PatientListCommand.COMMAND_WORD + " 3") instanceof PatientListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        PatientSelectCommand command = (PatientSelectCommand) parser.parseCommand(
                PatientSelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new PatientSelectCommand(INDEX_FIRST_PERSON), command);
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
