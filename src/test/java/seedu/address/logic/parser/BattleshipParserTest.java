package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AttackCommand;
import seedu.address.logic.commands.BeginCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.InitialiseMapCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListTagsCommand;
import seedu.address.logic.commands.PutShipCommand;
import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.commands.StatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class BattleshipParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final BattleshipParser parser = new BattleshipParser();

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_init() throws Exception {
        assertTrue(parser.parseCommand(InitialiseMapCommand.COMMAND_WORD + " " + InitialiseMapCommand.MINIMUM_MAP_SIZE)
                instanceof InitialiseMapCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_put() throws Exception {
        assertTrue(parser.parseCommand(
            PutShipCommand.COMMAND_WORD
            + " n/destroyer"
            + " r/vertical"
            + " c/a1") instanceof PutShipCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listTags() throws Exception {
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD) instanceof ListTagsCommand);
    }

    @Test
    public void parseCommand_begin() throws Exception {
        assertTrue(parser.parseCommand(BeginCommand.COMMAND_WORD) instanceof BeginCommand);
    }

    @Test
    public void parseCommand_attack() throws Exception {
        assertTrue(parser.parseCommand(AttackCommand.COMMAND_WORD + " a1") instanceof AttackCommand);
    }

    @Test
    public void parseCommand_stats() throws Exception {
        assertTrue(parser.parseCommand(StatsCommand.COMMAND_WORD) instanceof StatsCommand);
        assertTrue(parser.parseCommand(StatsCommand.COMMAND_WORD + " 3") instanceof StatsCommand);
    }

    @Test
    public void parseCommand_save() throws Exception {
        assertTrue(parser.parseCommand(SaveCommand.COMMAND_WORD) instanceof SaveCommand);
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
