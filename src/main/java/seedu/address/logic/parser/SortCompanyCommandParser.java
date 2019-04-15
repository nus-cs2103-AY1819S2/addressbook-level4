package seedu.address.logic.parser;

import seedu.address.logic.commands.CompanyComparator;
import seedu.address.logic.commands.SortCompanyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Company;

import java.util.Comparator;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class SortCompanyCommandParser {

    public SortCompanyCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] keywords = trimmedArgs.split("\\s+");


        try {
            Comparator<Company> Compare_function = CompanyComparator.GetFunction(keywords[0]);
            return new SortCompanyCommand(Compare_function, keywords[0], keywords[1]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCompanyCommand.MESSAGE_USAGE),pe);
        }
    }
}
