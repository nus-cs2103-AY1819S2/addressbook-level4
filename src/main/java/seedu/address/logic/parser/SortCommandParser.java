package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Comparator;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.SortPatientCommand;
import seedu.address.logic.commands.SortRecordCommand;
import seedu.address.logic.comparators.PatientComparator;
import seedu.address.logic.comparators.RecordComparator;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Patient;
import seedu.address.model.record.Record;
import seedu.address.ui.MainWindow;


/**
 * Parses input and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    @Override
    public SortCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        String[] inputArr = userInput.trim().split("\\s+");

        if (MainWindow.isGoToMode()) {
            //Record Sorting
            Comparator<Record> recordComparator;
            try {
                recordComparator = RecordComparator.getRecordComparator(inputArr[0]);
                boolean isReverse = orderChecker(inputArr);
                return new SortRecordCommand(recordComparator, inputArr[0], isReverse);
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), pe);
            }
        } else {
            //Patient Sorting
            Comparator<Patient> userComparator;
            try {
                userComparator = PatientComparator.getPatientComparator(inputArr[0]);
                boolean isReverse = orderChecker(inputArr);
                return new SortPatientCommand(userComparator, inputArr[0], isReverse);
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), pe);
            }
        }
    }

    /**
     * Checks if order is reverse or not.
     */
    private boolean orderChecker(String[] inputArr) throws ParseException {
        if (inputArr.length > 2) {
            throw new ParseException("");
        } else if (inputArr.length == 2) {
            switch (inputArr[1]) {

            case "desc":
                return true;

            case "asce":
                return false;

            default:
                throw new ParseException("");
            }
        }
        return false;
    }
}
