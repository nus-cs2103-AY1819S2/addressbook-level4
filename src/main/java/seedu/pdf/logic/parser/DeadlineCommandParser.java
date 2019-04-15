package seedu.pdf.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.pdf.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_DEADLINE_DONE;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_DEADLINE_NEW;
import static seedu.pdf.logic.parser.CliSyntax.PREFIX_DEADLINE_REMOVE;

import seedu.pdf.commons.core.index.Index;
import seedu.pdf.logic.commands.DeadlineCommand;
import seedu.pdf.logic.parser.exceptions.ParseException;
import seedu.pdf.model.pdf.Deadline;

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
        DeadlineCommand.DeadlineAction action;

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
                    && !argMultimap.getValue(PREFIX_DEADLINE_DONE).isPresent())
                || (argMultimap.getValue(PREFIX_DEADLINE_NEW).isPresent()
                    && (argMultimap.getValue(PREFIX_DEADLINE_DONE).isPresent()
                    || argMultimap.getValue(PREFIX_DEADLINE_REMOVE).isPresent()))) {

            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeadlineCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_DEADLINE_NEW).isPresent()) {
            deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE_NEW).get());
            action = DeadlineCommand.DeadlineAction.NEW;
        } else if (argMultimap.getValue(PREFIX_DEADLINE_DONE).isPresent()) {
            action = DeadlineCommand.DeadlineAction.DONE;
        } else if (argMultimap.getValue(PREFIX_DEADLINE_REMOVE).isPresent()) {
            action = DeadlineCommand.DeadlineAction.REMOVE;
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeadlineCommand.MESSAGE_USAGE));
        }

        return new DeadlineCommand(index, deadline, action);
    }
}
