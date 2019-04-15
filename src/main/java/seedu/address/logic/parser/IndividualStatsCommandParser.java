package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.IndividualStatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.project.ProjectName;

/**
 * Parses arguments and creates a new {@code InvididualStatsCommand}.
 */
public class IndividualStatsCommandParser implements Parser<IndividualStatsCommand> {

    private static final Pattern INDIVIDUAL_STATS_COMMAND_FORMAT = Pattern.compile("(?<arguments>.*)");
    private static final String INTEGER_STRING_FORMAT = "(-?)[0-9]+";

    /**
     * Parses the given {@code String} of arguments in the context of the IndividualStatsCommand
     * and returns a IndividualStatsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public IndividualStatsCommand parse(String args) throws ParseException {

        final Matcher matcher = INDIVIDUAL_STATS_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    IndividualStatsCommand.MESSAGE_USAGE));
        }
        final String arguments = matcher.group("arguments");
        String argument = arguments.trim();
        if (argument.matches(INTEGER_STRING_FORMAT)) {
            try {
                Index index = ParserUtil.parseIndex(argument);
                return new IndividualStatsCommand(index);
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        IndividualStatsCommand.MESSAGE_USAGE, pe));
            }
        } else {
            return new IndividualStatsCommand(new ProjectName(argument));
        }
    }
}
