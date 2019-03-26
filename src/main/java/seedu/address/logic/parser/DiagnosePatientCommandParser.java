package seedu.address.logic.parser;

import static seedu.address.logic.commands.DiagnosePatientCommand.MESSAGE_USAGE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import seedu.address.logic.commands.DiagnosePatientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.consultation.Assessment;
import seedu.address.model.consultation.Diagnosis;
import seedu.address.model.consultation.Symptom;

/**
 * Parse symptoms and assessment added by user to create a diagnosis for the patient
 */
public class DiagnosePatientCommandParser implements Parser<DiagnosePatientCommand> {

    public static final Prefix PREFIX_ASSESSMENT = new Prefix("a/");
    public static final Prefix PREFIX_SYMPTOM = new Prefix("s/");
    public static final String INVALID_ARGUMENTS_DIAGNOSIS = "Invalid arguments added for diagnosis.\n"
            + MESSAGE_USAGE;

    @Override
    public DiagnosePatientCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ASSESSMENT, PREFIX_SYMPTOM);

        if (!arePrefixesPresent(argMultimap, PREFIX_ASSESSMENT, PREFIX_SYMPTOM)) {
            throw new ParseException(INVALID_ARGUMENTS_DIAGNOSIS);
        }

        Assessment assessment = new Assessment(argMultimap.getValue(PREFIX_ASSESSMENT).get());
        ArrayList<Symptom> symptoms = parseSymptoms(argMultimap.getAllValues(PREFIX_SYMPTOM));

        return new DiagnosePatientCommand(new Diagnosis(assessment, symptoms));
    }

    // parsing methods
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * consolidate the list of symptoms entered by user into a single arraylist
     */
    public static ArrayList<Symptom> parseSymptoms(Collection<String> symptoms) {
        final ArrayList<Symptom> symptomList = new ArrayList<>();
        for (String symptom : symptoms) {
            symptomList.add(new Symptom(symptom));
        }
        return symptomList;
    }
}
