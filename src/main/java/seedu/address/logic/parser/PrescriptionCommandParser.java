package seedu.address.logic.parser;

import static seedu.address.logic.commands.PrescriptionCommand.MESSAGE_USAGE;

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

    public static final String INVALID_ARGUMENTS_PRESCRIPTION =
            "Invalid arguments entered\n" + MESSAGE_USAGE;
    public static final String INSUFFICIENT_QUANTITIES = "Some medicine do not have assigned quantity.\n";
    public static final String EXTRA_QUANTITIES = "Additional quantities specified.\n";

    @Override
    public PrescriptionCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MEDICINE, PREFIX_QUANTITY);

        if (!arePrefixesPresent(argMultimap, PREFIX_MEDICINE, PREFIX_QUANTITY)) {
            throw new ParseException(INVALID_ARGUMENTS_PRESCRIPTION);
        }

        ArrayList<String> medList = (ArrayList<String>) argMultimap.getAllValues(PREFIX_MEDICINE);
        ArrayList<Integer> qtyList = (ArrayList<Integer>) argMultimap.getAllValues(PREFIX_QUANTITY).stream()
                .map(Integer::valueOf).collect(Collectors.toList());

        if (medList.size() > qtyList.size()) {
            throw new ParseException(INSUFFICIENT_QUANTITIES);
        }

        if (medList.size() < qtyList.size()) {
            throw new ParseException(EXTRA_QUANTITIES);
        }

        return new PrescriptionCommand(medList, qtyList);
    }

    // parsing methods
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
