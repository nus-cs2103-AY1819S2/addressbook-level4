package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDateTime;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddAppCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddAppCommandParser implements Parser<AddAppCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AppAddCommand
     * and returns an AppAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppCommand parse(String args) throws ParseException {
        // TODO: Parse properly (can use String.trim)
        try {
            // token: index, date, start, end
            String[] token = args.split("\\s");
            //System.out.println(args);
            //System.out.println(token[1]);
            Index index = ParserUtil.parseIndex(token[1]);
            //System.out.println("this working");

            char[] value = new char[2];
            value[0] = token[2].charAt(0);
            value[1] = token[2].charAt(1);
            Integer day = Integer.parseInt(new String(value));

            value[0] = token[2].charAt(2);
            value[1] = token[2].charAt(3);
            Integer month = Integer.parseInt(new String(value));

            value[0] = token[2].charAt(4);
            value[1] = token[2].charAt(5);
            int year = Integer.parseInt(new String(value));
            year += 2000;

            value[0] = token[3].charAt(0);
            value[1] = token[3].charAt(1);
            Integer startHour = Integer.parseInt(new String(value));

            value[0] = token[3].charAt(2);
            value[1] = token[3].charAt(3);
            Integer startMin = Integer.parseInt(new String(value));

            value[0] = token[4].charAt(0);
            value[1] = token[4].charAt(1);
            Integer endHour = Integer.parseInt(new String(value));

            value[0] = token[4].charAt(2);
            value[1] = token[4].charAt(3);
            Integer endMin = Integer.parseInt(new String(value));

            LocalDateTime start = LocalDateTime.of(year, month, day, startHour, startMin);
            LocalDateTime end = LocalDateTime.of(year, month, day, endHour, endMin);

            return new AddAppCommand(index, start, end);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppCommand.MESSAGE_USAGE), pe);
        }
    }

}
