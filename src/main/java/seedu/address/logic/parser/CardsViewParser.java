package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.logic.commands.AddCardCommand;
import seedu.address.logic.commands.BackCommand;
import seedu.address.logic.commands.ClearCardCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCardCommand;
import seedu.address.logic.commands.EditCardCommand;
import seedu.address.logic.commands.FindCardCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCardCommand;
import seedu.address.logic.commands.StudyDeckCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CardsView;

/**
 * Parser used by CardsView.
 */
public class CardsViewParser implements ViewStateParser {

    private CardsView cardsView;

    public CardsViewParser(CardsView cardsView) {
        this.cardsView = cardsView;
    }

    @Override
    public Command parse(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
            case AddCardCommand.COMMAND_WORD:
                return new AddCardCommandParser(cardsView).parse(arguments);
            case ClearCardCommand.COMMAND_WORD:
                return new ClearCardCommand(cardsView);
            case DeleteCardCommand.COMMAND_WORD:
                return new DeleteCardCommandParser(cardsView).parse(arguments);
            case EditCardCommand.COMMAND_WORD:
                return new EditCardCommandParser(cardsView).parse(arguments);
            case FindCardCommand.COMMAND_WORD:
                return new FindCardCommandParser(cardsView).parse(arguments);
            case SelectCardCommand.COMMAND_WORD:
                return new SelectCardCommandParser(cardsView).parse(arguments);
            case BackCommand.COMMAND_WORD:
                return new BackCommand();
            case StudyDeckCommand.COMMAND_WORD:
                return new StudyDeckCommand(cardsView.getActiveDeck());
            case UndoCommand.COMMAND_WORD:
                return new UndoCommand(cardsView);
            case RedoCommand.COMMAND_WORD:
                return new RedoCommand(cardsView);
            case ListCommand.COMMAND_WORD:
                return new ListCommand(cardsView);
            default:
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
