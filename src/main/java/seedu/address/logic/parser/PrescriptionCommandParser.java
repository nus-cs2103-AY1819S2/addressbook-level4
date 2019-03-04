package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.PrescriptionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * parse arguments from user entered command to prescribe a list of medicine
 * to tackle the patient's current symptoms and illness
 */
public class PrescriptionCommandParser implements Parser<PrescriptionCommand> {

    public static final Prefix PREFIX_MEDICINE = new Prefix("m/");
    public static final Prefix PREFIX_QUANTITY = new Prefix("q/");

    @Override
    public PrescriptionCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEDICINE, PREFIX_QUANTITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEDICINE, PREFIX_QUANTITY)) {
            throw new ParseException("Some details are left out, please retype the command");
        }

        ArrayList<String> medList = (ArrayList<String>) argMultimap.getAllValues(PREFIX_MEDICINE);
        ArrayList<Integer> qtyList = (ArrayList<Integer>) argMultimap.getAllValues(PREFIX_QUANTITY).stream()
                .map(Integer::valueOf).collect(Collectors.toList());

        if (medList.size() > qtyList.size()) {
            throw new ParseException("Some medicine do not have assigned quantity, please reenter command");
        }

        if (medList.size() < qtyList.size()) {
            throw new ParseException("Additional quantities specified, please retype the command");
        }

        return new PrescriptionCommand(medList, qtyList);
    }

    // parsing methods
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
