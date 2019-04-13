package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveFromListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.job.JobListName;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class RemoveFromListCommandParser implements Parser<RemoveFromListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPersonToJobCommand
     * and returns an AddPersonToJobCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveFromListCommand parse(String args) throws ParseException {

        JobListName chosenList;
        ArrayList<Index> indexes = new ArrayList<>();

        try {
            chosenList = ParserUtil.parseJobListName(args.split("\\b\\s")[0].trim());
        } catch (Exception e) {
            throw new ParseException(RemoveFromListCommand.MESSAGE_NO_LIST_NAME + "\n"
                    + RemoveFromListCommand.MESSAGE_USAGE);
        }

        try {
            String indexString = args.split("\\b\\s")[1].trim();
            ArrayList<String> numbers = new ArrayList<>(Arrays.asList(indexString.split("[,\\s]+")));
            for (int i = 0; i < numbers.size(); i++) {
                try {
                    indexes.add(ParserUtil.parseIndex(numbers.get(i)));
                } catch (ParseException pe) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveFromListCommand.MESSAGE_BAD_INDEX), pe);
                }
            }
        } catch (Exception e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveFromListCommand.MESSAGE_USAGE), e);
        }


        return new RemoveFromListCommand(chosenList, indexes);
    }

}
