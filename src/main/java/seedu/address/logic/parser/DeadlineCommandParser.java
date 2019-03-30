package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_DONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_NEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_REMOVE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeadlineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.pdf.Deadline;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeadlineCommandParser implements Parser<DeadlineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeadlineCommand
     * and returns an DeadlineCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DeadlineCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DEADLINE_NEW, PREFIX_DEADLINE_DONE, PREFIX_DEADLINE_REMOVE);

        Index index;
        Deadline deadline = new Deadline();

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeadlineCommand.MESSAGE_USAGE), pe);
        }

        //If both Remove & Done are present or none of the prefixes are present.
        if ((argMultimap.getValue(PREFIX_DEADLINE_DONE).isPresent()
                && argMultimap.getValue(PREFIX_DEADLINE_REMOVE).isPresent())
                || (!argMultimap.getValue(PREFIX_DEADLINE_NEW).isPresent()
                && !argMultimap.getValue(PREFIX_DEADLINE_REMOVE).isPresent()
                && !argMultimap.getValue(PREFIX_DEADLINE_DONE).isPresent())) {

            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeadlineCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_DEADLINE_NEW).isPresent()) {
            deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE_NEW).get());
        }

        if (argMultimap.getValue(PREFIX_DEADLINE_DONE).isPresent()) {
            deadline = Deadline.setDone(deadline);
        } else if (argMultimap.getValue(PREFIX_DEADLINE_REMOVE).isPresent()) {
            deadline = Deadline.setRemove(deadline);
        }

        return new DeadlineCommand(index, deadline);
    }
}
