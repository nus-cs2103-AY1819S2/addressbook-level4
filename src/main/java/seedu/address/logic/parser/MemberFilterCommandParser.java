package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.MemberFilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MemberFilterCommand object
 */
public class MemberFilterCommandParser implements Parser<MemberFilterCommand> {
    private final String[] keywords = {"gender", "major", "tags", "yearofstudy"};

    /**
     * Parses the given {@code String} of arguments in the context of the MemberFindCommand
     * and returns an MemberFindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public MemberFilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemberFilterCommand.MESSAGE_USAGE));
        }

        String[] input = trimmedArgs.split(" ");
        if (input.length < 2 || !isCriteriaValid(input[0])) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemberFilterCommand.MESSAGE_USAGE));
        }
        String[] criteriaAndKeyword = combineKeywords(input);
        return new MemberFilterCommand(criteriaAndKeyword);

    }

    /**
     * to check if the filter type is valid.
     */
    private Boolean isCriteriaValid(String criteria) {
        for (int i = 0; i < keywords.length; i++) {
            if (criteria.equalsIgnoreCase(keywords[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Concatenate keywords from the input.
     */
    private String[] combineKeywords(String[] input) {
        String[] keywordsArray = Arrays.copyOfRange(input, 1, input.length);
        String keywords = String.join(" ", keywordsArray);
        String[] criteriaAndKeyword = new String[2];
        criteriaAndKeyword[0] = input[0]; //store criteria
        criteriaAndKeyword[1] = keywords; //store keywords
        return criteriaAndKeyword;
    }
}
