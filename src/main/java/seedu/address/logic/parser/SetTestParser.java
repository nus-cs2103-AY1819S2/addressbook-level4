package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.Syntax.PREFIX_CORE_ANSWER;
import static seedu.address.logic.parser.Syntax.PREFIX_CORE_QUESTION;

import java.util.Optional;

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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CORE_QUESTION, PREFIX_CORE_ANSWER);

        Optional<String> question = argMultimap.getValue(PREFIX_CORE_QUESTION);
        Optional<String> answer = argMultimap.getValue(PREFIX_CORE_ANSWER);

        if (!question.isPresent()
                || !answer.isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetTestCommand.MESSAGE_USAGE));
        } else {
            int qIndex = Integer.parseInt(question.get());
            int aIndex = Integer.parseInt(answer.get());
            Index questionIndex = Index.fromOneBased(qIndex);
            Index answerIndex = Index.fromOneBased(aIndex);

            return new SetTestCommand(questionIndex, answerIndex);
        }
    }
}
