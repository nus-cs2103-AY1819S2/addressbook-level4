package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quickdocs.logic.commands.PurchaseMedicineCommand;
import quickdocs.logic.commands.PurchaseMedicineViaPathCommand;
import quickdocs.logic.commands.PurchaseMedicineWoPathCommand;
import quickdocs.logic.parser.exceptions.ParseException;

/**
 * Parse input arguments and returns a PurchaseMedicineCommand if input is in correct format
 */
public class PurchaseMedicineCommandParser implements Parser<PurchaseMedicineCommand> {

    private static final Pattern PurchaseMedicineViaPathCommand_Argument_Format =
            Pattern.compile("(?<rawPath>\\S+)(?:\\s+)(?<purchaseInformation>\\d.+)");
    private static final Pattern PurchaseMedicineWOPathCommand_Argument_Format =
            Pattern.compile("(?<medicineName>[^(\\s)(\\\\)]+)(?:\\s+)(?<purchaseInformation>\\d.+)");
    private static final Pattern PurchaseInformation_Format =
            Pattern.compile("(?<quantity>\\d+)(?:\\s+)(?<cost>\\d+\\.?\\d*)");

    /**
     * Parse the input string to form a PurchaseMedicineCommand
     * @param args the input
     * @return A PurchaseMedicineCommand corresponding to the input
     * @throws ParseException if the user input does not conform to the format
     */
    public PurchaseMedicineCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PurchaseMedicineCommand.MESSAGE_USAGE));
        }
        final Matcher medicinePurchaseViaPath = PurchaseMedicineViaPathCommand_Argument_Format.matcher(trimmedArgs);
        final Matcher medicinePurchaseWoPath = PurchaseMedicineWOPathCommand_Argument_Format.matcher(trimmedArgs);
        if (medicinePurchaseWoPath.matches()) {
            final String medicineName = medicinePurchaseWoPath.group("medicineName");
            final String purchaseInfo = medicinePurchaseWoPath.group("purchaseInformation");
            final Matcher quantityCost = PurchaseInformation_Format.matcher(purchaseInfo);
            if (!quantityCost.matches()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PurchaseMedicineCommand.MESSAGE_USAGE));
            }
            String quantity = quantityCost.group("quantity");
            String cost = quantityCost.group("cost");
            return new PurchaseMedicineWoPathCommand(medicineName, Integer.parseInt(quantity), new BigDecimal(cost));
        } else if (medicinePurchaseViaPath.matches()) {
            final String rawPath = medicinePurchaseViaPath.group("rawPath");
            final String purchaseInfo = medicinePurchaseViaPath.group("purchaseInformation");
            String[] path = rawPath.split("\\\\");
            final Matcher quantityCost = PurchaseInformation_Format.matcher(purchaseInfo);
            if (!quantityCost.matches()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PurchaseMedicineCommand.MESSAGE_USAGE));
            }
            String quantity = quantityCost.group("quantity");
            String cost = quantityCost.group("cost");
            return new PurchaseMedicineViaPathCommand(path, Integer.parseInt(quantity), new BigDecimal(cost));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PurchaseMedicineCommand.MESSAGE_USAGE));
        }
    }
}
