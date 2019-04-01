package seedu.knowitall.logic.parser;

import static seedu.knowitall.commons.core.Messages.MESSAGE_ILLEGAL_OPTION_CANNOT_BE_SAME_AS_ANSWER;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_HINT;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.knowitall.logic.parser.CliSyntax.PREFIX_QUESTION;

import java.util.Set;

import seedu.knowitall.logic.commands.AddCommand;
import seedu.knowitall.logic.parser.exceptions.ParseException;
import seedu.knowitall.model.card.Answer;
import seedu.knowitall.model.card.Card;
import seedu.knowitall.model.card.Option;
import seedu.knowitall.model.card.Question;
import seedu.knowitall.model.card.Score;
import seedu.knowitall.model.hint.Hint;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                        args, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_HINT, PREFIX_OPTION);


        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_ANSWER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Question question = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get());
        Answer answer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get());
        // Default score is 0/0
        Score score = new Score(0, 0);
        Set<Hint> hintList = ParserUtil.parseHints(argMultimap.getAllValues(PREFIX_HINT));
        Set<Option> optionList = ParserUtil.parseOptions(argMultimap.getAllValues(PREFIX_OPTION));
        // Check if options list contains answer
        for (Option option: optionList) {
            if (option.optionValue.equalsIgnoreCase(answer.fullAnswer)) {
                throw new ParseException(MESSAGE_ILLEGAL_OPTION_CANNOT_BE_SAME_AS_ANSWER);
            }
        }

        Card card = new Card(question, answer, score, optionList, hintList);

        return new AddCommand(card);
    }



}
