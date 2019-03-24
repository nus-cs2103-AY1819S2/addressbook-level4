package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_COMPLETE;
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
                ArgumentTokenizer.tokenize(args, PREFIX_DEADLINE_NEW, PREFIX_DEADLINE_COMPLETE, PREFIX_DEADLINE_REMOVE);

        Index index;
        Deadline deadline = null;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeadlineCommand.MESSAGE_USAGE), pe);
        }

        if (argMultimap.getValue(PREFIX_DEADLINE_NEW).isPresent()) {
            deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE_NEW).get(),
                    Deadline.STATUS_READY);

            return new DeadlineCommand(index, deadline);
        } else if (argMultimap.getValue(PREFIX_DEADLINE_COMPLETE).isPresent()) {
            System.out.println(argMultimap.getValue(PREFIX_DEADLINE_COMPLETE).get());
            return new DeadlineCommand(index, deadline, Deadline.STATUS_COMPLETE);
        } else if (argMultimap.getValue(PREFIX_DEADLINE_REMOVE).isPresent()) {
            System.out.println(argMultimap.getValue(PREFIX_DEADLINE_REMOVE).get());
            return new DeadlineCommand(index, deadline, Deadline.STATUS_REMOVE);
        } else {
            throw new ParseException("Missing Prefix(s)");
        }

    }
}
