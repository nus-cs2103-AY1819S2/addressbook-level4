package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddMedicineCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input argument and returns an AddMedicineCommand
 */
public class AddMedicineCommandParser implements Parser<AddMedicineCommand> {

    private static final Pattern AddMedicineCommand_Argument_Format =
            Pattern.compile("(?<rawPath>\\S+)(?:\\s+)(?<medicineInformation>\\S.+)");
    private static final Pattern MedicineInformation_Format =
            Pattern.compile("(?<name>\\S+)(?:\\s*)(?<priceAndQuantity>.*)");

    /**
     * parse the given input to produce a AddMedicineCommand
     * @param args the input
     * @return An AddMedicineCommand for execution
     * @throws ParseException if the user input does not conform the format
     */
    public AddMedicineCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
        }
        final Matcher pathMedicine = AddMedicineCommand_Argument_Format.matcher(trimmedArgs);
        if (!pathMedicine.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
        }
        final String rawPath = pathMedicine.group("rawPath");
        final String medicineInfo = pathMedicine.group("medicineInformation");
        String[] path = rawPath.split("\\\\");
        final Matcher nameQuantity = MedicineInformation_Format.matcher(medicineInfo);
        if (!nameQuantity.matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
        }
        String medicineName = nameQuantity.group("name");
        String quantity = nameQuantity.group("quantity");
        String price = nameQuantity.group("price");
        if (quantity.isEmpty()) {
            return new AddMedicineCommand(path, medicineName, new BigDecimal(price));
        } else {
            return new AddMedicineCommand(path, medicineName, Integer.parseInt(quantity), new BigDecimal(price));
        }
    }
}
