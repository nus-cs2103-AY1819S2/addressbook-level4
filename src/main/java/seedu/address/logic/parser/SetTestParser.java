package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;

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
        List<String> testValues = Arrays.asList(args.trim().split(" "));
        try {
            // Only allow 2 inputs - can only set 2 values to be tested
            if (testValues.size() > 2) {
                throw new ParseException("");
            }

            String testValue = testValues.get(0);
            Index questionIndex = ParserUtil.parseIndex(testValue);
            testValue = testValues.get(1);
            Index answerIndex = ParserUtil.parseIndex(testValue);

            // Only allow 2 distinct inputs - must set 2 values to be tested
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
