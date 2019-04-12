package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.management.SetTestCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link SetTestCommand} object.
 */
public class SetTestParser implements Parser<SetTestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * {@link SetTestCommand} and returns an {@link SetTestCommand} object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetTestCommand parse(String args) throws ParseException {
        String[] testValues = args.split(" ");

        try {
            Index questionIndex = ParserUtil.parseIndex(testValues[0]);
            Index answerIndex = ParserUtil.parseIndex(testValues[1]);

            if (questionIndex.equals(answerIndex)) {
                throw new ParseException("");
            }

            return new SetTestCommand(questionIndex, answerIndex);
        } catch (ParseException | IndexOutOfBoundsException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            SetTestCommand.MESSAGE_USAGE), e);
        }
    }
}
