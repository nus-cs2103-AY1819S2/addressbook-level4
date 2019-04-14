package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.OpenDeckCommandParser;
import seedu.address.logic.StudyDeckCommandParser;
import seedu.address.logic.commands.AddDeckCommand;
import seedu.address.logic.commands.ClearDeckCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteDeckCommand;
import seedu.address.logic.commands.EditDeckCommand;
import seedu.address.logic.commands.ExportDeckCommand;
import seedu.address.logic.commands.FindDeckCommand;
import seedu.address.logic.commands.ImportDeckCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.OpenDeckCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectDeckCommand;
import seedu.address.logic.commands.StudyDeckCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.DecksView;

/**
 * Parser used by DecksView.
 */
public class DecksViewParser implements ViewStateParser {

    private DecksView decksView;

    public DecksViewParser(DecksView decksView) {
        this.decksView = decksView;
    }

    @Override
    public Command parse(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
            case AddDeckCommand.COMMAND_WORD:
                return new AddDeckCommandParser(decksView).parse(arguments);
            case ClearDeckCommand.COMMAND_WORD:
                return new ClearDeckCommand();
            case SelectDeckCommand.COMMAND_WORD:
                return new SelectDeckCommandParser(decksView).parse(arguments);
            case OpenDeckCommand.COMMAND_WORD:
                return new OpenDeckCommandParser(decksView).parse(arguments);
            case StudyDeckCommand.COMMAND_WORD:
                return new StudyDeckCommandParser(decksView).parse(arguments);
            case DeleteDeckCommand.COMMAND_WORD:
                return new DeleteDeckCommandParser(decksView).parse(arguments);
            case EditDeckCommand.COMMAND_WORD:
                return new EditDeckCommandParser(decksView).parse(arguments);
            case FindDeckCommand.COMMAND_WORD:
                return new FindDeckCommandParser(decksView).parse(arguments);
            case ExportDeckCommand.COMMAND_WORD:
                return new ExportDeckCommandParser().parse(arguments);
            case ImportDeckCommand.COMMAND_WORD:
                return new ImportDeckCommandParser().parse(arguments);
            case UndoCommand.COMMAND_WORD:
                return new UndoCommand(decksView);
            case RedoCommand.COMMAND_WORD:
                return new RedoCommand(decksView);
            case ListCommand.COMMAND_WORD:
                return new ListCommand(decksView);
            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
