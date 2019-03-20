package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Card;

/**
 * Shows the answer to the question
 */
public class ShowAnswerCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Answer";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        model.setCurrentStudyState(Model.studyState.ANSWER);
        model.updateTextShown();
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }


}
