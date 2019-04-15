package seedu.address.logic.parser;

import static java.lang.Integer.parseInt;

import seedu.address.logic.commands.GenerateQuestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.StudyView;

/**
 * Parses input arguments and creates a new GenerateQuestionCommand object
 */
public class GenerateQuestionCommandParser implements Parser<GenerateQuestionCommand> {

    public static final String MESSAGE_INVALID_RATING = "Answer is not an integer.";
    public static final String MESSAGE_EXCEED_RATING = "Rating exceeds range (1-5).";

    private final StudyView studyView;

    public GenerateQuestionCommandParser(StudyView studyView) {
        this.studyView = studyView;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the GenerateQuestionCommand
     * and returns an GenerateQuestionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateQuestionCommand parse(String commandWord) throws ParseException {
        try {
            Integer rating = parseInt(commandWord);
            if (rating < 1 || rating > 5) {
                throw new ParseException(MESSAGE_EXCEED_RATING);
            }
            return new GenerateQuestionCommand(studyView, rating);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_RATING);
        }
    }
}
