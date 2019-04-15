package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.BlackWhiteCommand;
import seedu.address.logic.commands.BrightnessCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ContrastCommand;
import seedu.address.logic.commands.CropCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListFilesCommand;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ResizeCommand;
import seedu.address.logic.commands.RotateCommand;
import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.commands.SavePresetCommand;
import seedu.address.logic.commands.SetPresetCommand;
import seedu.address.logic.commands.TabCommand;
import seedu.address.logic.commands.UndoCommand;

import seedu.address.logic.commands.WaterMarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class FomoFotoParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        case ListFilesCommand.COMMAND_WORD:
            return new ListFilesCommand();

        case SaveCommand.COMMAND_WORD:
            return new SaveCommandParser().parse(arguments);

        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);

        case RotateCommand.COMMAND_WORD:
            return new RotateCommandParser().parse(arguments);

        case OpenCommand.COMMAND_WORD:
            return new OpenCommandParser().parse(arguments);

        case ContrastCommand.COMMAND_WORD:
            return new ContrastCommandParser().parse(arguments);

        case CropCommand.COMMAND_WORD:
            return new CropCommandParser().parse(arguments);

        case BrightnessCommand.COMMAND_WORD:
            return new BrightnessCommandParser().parse(arguments);

        case BlackWhiteCommand.COMMAND_WORD:
            return new BlackWhiteCommandParser().parse(arguments);

        case TabCommand.COMMAND_WORD:
            return new TabCommand();

        case ResizeCommand.COMMAND_WORD:
            return new ResizeCommandParser().parse(arguments);

        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);
        case SavePresetCommand.COMMAND_WORD:
            return new SavePresetCommandParser().parse(arguments);
        case SetPresetCommand.COMMAND_WORD:
            return new SetPresetCommandParser().parse(arguments);
        case WaterMarkCommand.COMMAND_WORD:
            return new WaterMarkCommandParser().parse(arguments);
        default: throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
