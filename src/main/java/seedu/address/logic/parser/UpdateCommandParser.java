package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BATCHNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.UpdateBatchDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_BATCHNUMBER, PREFIX_QUANTITY,
                PREFIX_EXPIRY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE), pe);
        }

        UpdateBatchDescriptor updateBatchDescriptor = new UpdateBatchDescriptor();

        if (!argMultimap.getValue(PREFIX_BATCHNUMBER).isPresent()
                || !(argMultimap.getValue(PREFIX_EXPIRY).isPresent()
                || argMultimap.getValue(PREFIX_QUANTITY).isPresent())) {
            throw new ParseException(UpdateCommand.MESSAGE_MISSING_PARAMETER);
        }

        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            updateBatchDescriptor.setQuantity(ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get()));
        }

        if (argMultimap.getValue(PREFIX_EXPIRY).isPresent()) {
           updateBatchDescriptor.setExpiry(ParserUtil.parseExpiry(argMultimap.getValue(PREFIX_EXPIRY).get()));
        }

        updateBatchDescriptor.setBatchNumber(ParserUtil.parseBatchNumber(argMultimap.getValue(PREFIX_BATCHNUMBER)
                .get()));

        return new UpdateCommand(index, updateBatchDescriptor);
    }
}
