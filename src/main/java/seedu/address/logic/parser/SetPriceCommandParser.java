package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.SetPriceCommand;
import seedu.address.logic.commands.SetPriceViaPathCommand;
import seedu.address.logic.commands.SetPriceWoPathCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * A parser to parse user input to SetPriceCommand
 */
public class SetPriceCommandParser implements Parser<SetPriceCommand> {

    private static final Pattern SetPriceCommand_WoPathArgument_Format =
            Pattern.compile("(?<medicineName>[^(\\s)(\\\\)]+)(?:\\s+)(?<price>\\d+\\.*\\d*)");
    private static final Pattern SetPriceCommand_ViaPathArgument_Format =
            Pattern.compile("(?<rawPath>\\S+)(?:\\s+)(?<price>\\d+\\.*\\d*)");

    @Override
    public SetPriceCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPriceCommand.MESSAGE_USAGE));
        }
        Matcher argumentViaPath = SetPriceCommand_ViaPathArgument_Format.matcher(trimmedArgs);
        Matcher argumentWoPath = SetPriceCommand_WoPathArgument_Format.matcher(trimmedArgs);
        if (argumentWoPath.matches()) {
            String name = argumentWoPath.group("medicineName");
            String price = argumentWoPath.group("price");
            return new SetPriceWoPathCommand(name, new BigDecimal(price));
        } else if (argumentViaPath.matches()) {
            String rawPath = argumentViaPath.group("rawPath");
            String price = argumentViaPath.group("price");
            String[] path = rawPath.split("\\\\");
            return new SetPriceViaPathCommand(path, new BigDecimal(price));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPriceCommand.MESSAGE_USAGE));
        }
    }
}
