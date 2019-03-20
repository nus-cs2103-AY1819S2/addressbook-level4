package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.math.BigDecimal;
import java.util.Optional;
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
            Pattern.compile("(?<name>\\S+)(?:\\s*)(?<priceAndQuantity>\\s.*)?");
    private static final Pattern BigDecimal_Format = Pattern.compile("(?<int>\\d+)(?:\\.?)(?<decimal>\\d*)");
    private static final Pattern Integer_Format = Pattern.compile("\\d+");
    private static final Prefix PREFIX_PRICE = new Prefix("p/");
    private static final Prefix PREFIX_QUANTITY = new Prefix("q/");

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
        String[] path = rawPath.split("\\\\");
        final String medicineInfo = pathMedicine.group("medicineInformation");
        final Matcher namePriceQuantity = MedicineInformation_Format.matcher(medicineInfo);
        if (!namePriceQuantity.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
        }
        String medicineName = namePriceQuantity.group("name");
        String priceAndQuantity = namePriceQuantity.group("priceAndQuantity");
        if (priceAndQuantity == null) {
            return new AddMedicineCommand(path, medicineName, Optional.empty(), Optional.empty());
        }
        ArgumentMultimap map = ArgumentTokenizer.tokenize(priceAndQuantity, PREFIX_PRICE, PREFIX_QUANTITY);
        if ((!isPrefixesPresent(map, PREFIX_QUANTITY)
                || !isPrefixesPresent(map, PREFIX_PRICE)
                || !map.getPreamble().isEmpty())) {
            throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
        }
        String quantityString = map.getValue(PREFIX_QUANTITY).get().trim();
        String priceString = map.getValue(PREFIX_PRICE).get().trim();
        if (!Integer_Format.matcher(quantityString).matches() || !BigDecimal_Format.matcher(priceString).matches()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMedicineCommand.MESSAGE_USAGE));
        }
        return new AddMedicineCommand(path, medicineName,
                Optional.of(Integer.parseInt(quantityString)), Optional.of(new BigDecimal(priceString)));
    }

    /**
     * Returns true if the prefix contains a non-empty {@code Optional} value in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return argumentMultimap.getValue(prefix).isPresent();
    }

}
