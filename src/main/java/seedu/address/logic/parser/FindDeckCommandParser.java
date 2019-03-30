package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.DecksView;
import seedu.address.logic.commands.FindDeckCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.DeckNameContainsKeywordsPredicate;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses input arguments and creates a new FindDeckCommand object
 */
public class FindDeckCommandParser implements Parser<FindDeckCommand> {

    private DecksView decksView;

    public FindDeckCommandParser(DecksView decksView) {
        this.decksView = decksView;
    }

    private final String IN_BETWEEN_QUOTES_REGEX = "\"([^\"]*)\"";

    /**
     * Parses the given {@code String} of arguments in the context of the FindDeckCommand
     * and returns an FindDeckCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDeckCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeckCommand.MESSAGE_USAGE));
        }

        ArrayList<String> nameKeyword = new ArrayList<>();
        Pattern p = Pattern.compile( IN_BETWEEN_QUOTES_REGEX );
        Matcher m = p.matcher( trimmedArgs );
        while (m.find()) {
            nameKeyword.add(m.group(1));
        }

        trimmedArgs = trimmedArgs.replaceAll(IN_BETWEEN_QUOTES_REGEX, "");
        String[] keyArgs = trimmedArgs.split("\\s+");

        for (String key: keyArgs) {
            if (!key.isEmpty()) {
                nameKeyword.add(key);
            }
        }

        return new FindDeckCommand(decksView, new DeckNameContainsKeywordsPredicate(nameKeyword));
    }
}
