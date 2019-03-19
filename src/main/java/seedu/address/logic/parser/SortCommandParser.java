package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Comparator;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.comparators.PatientComparator;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Patient;



/**
 * Parses input and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    @Override
    public SortCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        String[] inputArr = userInput.trim().split(" ");

        if (inputArr[0].trim().equals("record")) {
            //TODO: Sorting for records
            System.out.println("Record sorting");
            try {
                Comparator<Patient> recordComparator;

                recordComparator = PatientComparator.getPatientComparator(inputArr[0]);

                return new SortCommand(recordComparator, userInput.trim());

            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), pe);
            }
        } else {
            Comparator<Patient> userComparator;
            try {
                userComparator = PatientComparator.getPatientComparator(inputArr[0]);
                boolean isReverse = false;

                if (inputArr.length > 2) {
                    throw new ParseException("");
                } else if (inputArr.length == 2) {
                    switch (inputArr[1]) {

                    case "desc":
                        isReverse = true;
                        break;
                    case "asce":
                        isReverse = false;
                        break;
                    default:
                        throw new ParseException("");
                    }
                }
                return new SortCommand(userComparator, inputArr[0], isReverse);

            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), pe);
            }
        }
    }
}
