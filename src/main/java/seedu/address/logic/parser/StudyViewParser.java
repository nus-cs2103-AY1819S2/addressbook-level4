package seedu.address.logic.parser;

import seedu.address.logic.commands.BackCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.OpenDeckCommand;
import seedu.address.logic.commands.ShowAnswerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.StudyView;

/**
 * Parser used by StudyView.
 */
public class StudyViewParser implements ViewStateParser {

    private StudyView studyView;

    public StudyViewParser(StudyView studyView) {
        this.studyView = studyView;
    }

    @Override
    public Command parse(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
            case OpenDeckCommand.COMMAND_WORD:
                return new OpenDeckCommand(studyView.getActiveDeck());
            case BackCommand.COMMAND_WORD:
                return new BackCommand();
            case ExitCommand.COMMAND_WORD:
                return new ExitCommand();
            case HelpCommand.COMMAND_WORD:
                return new HelpCommand();
            case HistoryCommand.COMMAND_WORD:
                return new HistoryCommand();
            default:
                if (studyView.isInQuestionState()) {
                    return new ShowAnswerCommand(commandWord + arguments);
                } else {
                    return new GenerateQuestionCommandParser().parse(commandWord);
                }
        }
    }

}
