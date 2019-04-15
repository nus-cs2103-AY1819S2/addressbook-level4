package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortPersonCommand;
import seedu.address.logic.commands.PersonComparator;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;

import java.util.Comparator;

public class SortPersonCommandParser {

    public SortPersonCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] keywords = trimmedArgs.split("\\s+");


        try {
            Comparator<Person> Compare_function = PersonComparator.GetFunction(keywords[0]);
            return new SortPersonCommand(Compare_function, keywords[0], keywords[1]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortPersonCommand.MESSAGE_USAGE),pe);
        }
    }

}
