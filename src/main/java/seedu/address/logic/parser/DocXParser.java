package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.appointment.AddAppointmentCommand;
import seedu.address.logic.commands.appointment.ListAppointmentCommand;
import seedu.address.logic.commands.appointment.MarkAppointmentCommand;
import seedu.address.logic.commands.doctor.AddDoctorCommand;
import seedu.address.logic.commands.doctor.DeleteDoctorCommand;
import seedu.address.logic.commands.doctor.DoctorMatchCommand;
import seedu.address.logic.commands.doctor.EditDoctorCommand;
import seedu.address.logic.commands.doctor.ListDoctorCommand;
import seedu.address.logic.commands.doctor.SelectDoctorCommand;
import seedu.address.logic.commands.medicalhistory.AddMedHistCommand;
import seedu.address.logic.commands.medicalhistory.EditMedHistCommand;
import seedu.address.logic.commands.medicalhistory.ListMedHistCommand;
import seedu.address.logic.commands.medicalhistory.SearchMedHistCommand;
import seedu.address.logic.commands.medicalhistory.SelectMedHistCommand;
import seedu.address.logic.commands.medicalhistory.SortMedHistCommand;
import seedu.address.logic.commands.patient.AddPatientCommand;
import seedu.address.logic.commands.patient.DeletePatientCommand;
import seedu.address.logic.commands.patient.EditPatientCommand;
import seedu.address.logic.commands.patient.ListPatientCommand;
import seedu.address.logic.commands.patient.SearchAdvancedPatientCommand;
import seedu.address.logic.commands.patient.SearchPatientApptStatusCommand;
import seedu.address.logic.commands.patient.SearchPatientCommand;
import seedu.address.logic.commands.patient.SearchPatientPidCommand;
import seedu.address.logic.commands.patient.SelectPatientCommand;
import seedu.address.logic.commands.prescription.AddPrescriptionCommand;
import seedu.address.logic.commands.prescription.EditPrescriptionCommand;
import seedu.address.logic.commands.prescription.ListPrescriptionCommand;
import seedu.address.logic.commands.prescription.SearchPrescriptionCommand;
import seedu.address.logic.commands.prescription.SelectPrescriptionCommand;
import seedu.address.logic.commands.prescription.SortPrescriptionCommand;
import seedu.address.logic.parser.appointment.AddAppointmentCommandParser;
import seedu.address.logic.parser.appointment.ListAppointmentCommandParser;
import seedu.address.logic.parser.appointment.MarkAppointmentCommandParser;
import seedu.address.logic.parser.doctor.AddDoctorCommandParser;
import seedu.address.logic.parser.doctor.DeleteDoctorCommandParser;
import seedu.address.logic.parser.doctor.DoctorMatchCommandParser;
import seedu.address.logic.parser.doctor.EditDoctorCommandParser;
import seedu.address.logic.parser.doctor.ListDoctorCommandParser;
import seedu.address.logic.parser.doctor.SelectDoctorCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.medicalhistory.AddMedHistCommandParser;
import seedu.address.logic.parser.medicalhistory.EditMedHistCommandParser;
import seedu.address.logic.parser.medicalhistory.ListMedHistCommandParser;
import seedu.address.logic.parser.medicalhistory.SearchMedHistCommandParser;
import seedu.address.logic.parser.medicalhistory.SelectMedHistCommandParser;
import seedu.address.logic.parser.medicalhistory.SortMedHistCommandParser;
import seedu.address.logic.parser.patient.AddPatientCommandParser;
import seedu.address.logic.parser.patient.EditPatientCommandParser;
import seedu.address.logic.parser.patient.SearchAdvancedPatientCommandParser;
import seedu.address.logic.parser.patient.SearchPatientApptStatusCommandParser;
import seedu.address.logic.parser.patient.SearchPatientCommandParser;
import seedu.address.logic.parser.patient.SearchPatientPidCommandParser;
import seedu.address.logic.parser.patient.SelectPatientCommandParser;
import seedu.address.logic.parser.prescription.AddPrescriptionCommandParser;
import seedu.address.logic.parser.prescription.EditPrescriptionCommandParser;
import seedu.address.logic.parser.prescription.ListPrescriptionCommandParser;
import seedu.address.logic.parser.prescription.SearchPrescriptionCommandParser;
import seedu.address.logic.parser.prescription.SelectPrescriptionCommandParser;
import seedu.address.logic.parser.prescription.SortPrescriptionCommandParser;

/**
 * Parses user input.
 */
public class DocXParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input
     *                        does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddPatientCommand.COMMAND_WORD:
            return new AddPatientCommandParser().parse(arguments);

        case ListPatientCommand.COMMAND_WORD:
            return new ListPatientCommand();

        case EditPatientCommand.COMMAND_WORD:
            return new EditPatientCommandParser().parse(arguments);

        case SearchPatientCommand.COMMAND_WORD:
            return new SearchPatientCommandParser().parse(arguments);

        case SearchPatientPidCommand.COMMAND_WORD:
            return new SearchPatientPidCommandParser().parse(arguments);

        case SearchPatientApptStatusCommand.COMMAND_WORD:
            return new SearchPatientApptStatusCommandParser().parse(arguments);

        case SearchAdvancedPatientCommand.COMMAND_WORD:
            return new SearchAdvancedPatientCommandParser().parse(arguments);

        case DeletePatientCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case AddDoctorCommand.COMMAND_WORD:
            return new AddDoctorCommandParser().parse(arguments);

        case AddAppointmentCommand.COMMAND_WORD:
            return new AddAppointmentCommandParser().parse(arguments);

        case ListAppointmentCommand.COMMAND_WORD:
            return new ListAppointmentCommandParser().parse(arguments);

        case MarkAppointmentCommand.COMMAND_WORD:
            return new MarkAppointmentCommandParser().parse(arguments);

        case ListDoctorCommand.COMMAND_WORD:
            return new ListDoctorCommandParser().parse(arguments);

        case EditDoctorCommand.COMMAND_WORD:
            return new EditDoctorCommandParser().parse(arguments);

        case DeleteDoctorCommand.COMMAND_WORD:
            return new DeleteDoctorCommandParser().parse(arguments);

        case SelectDoctorCommand.COMMAND_WORD:
            return new SelectDoctorCommandParser().parse(arguments);

        case DoctorMatchCommand.COMMAND_WORD:
            return (new DoctorMatchCommandParser().parse(arguments));

        case AddMedHistCommand.COMMAND_WORD:
            return new AddMedHistCommandParser().parse(arguments);

        case ListMedHistCommand.COMMAND_WORD:
            return new ListMedHistCommandParser().parse(arguments);

        case EditMedHistCommand.COMMAND_WORD:
            return new EditMedHistCommandParser().parse(arguments);

        case SelectPatientCommand.COMMAND_WORD:
            return new SelectPatientCommandParser().parse(arguments);

        case SearchMedHistCommand.COMMAND_WORD:
            return new SearchMedHistCommandParser().parse(arguments);

        case SelectMedHistCommand.COMMAND_WORD:
            return new SelectMedHistCommandParser().parse(arguments);

        case SortMedHistCommand.COMMAND_WORD:
            return new SortMedHistCommandParser().parse(arguments);

        case AddPrescriptionCommand.COMMAND_WORD:
            return new AddPrescriptionCommandParser().parse(arguments);

        case ListPrescriptionCommand.COMMAND_WORD:
            return new ListPrescriptionCommandParser().parse(arguments);

        case EditPrescriptionCommand.COMMAND_WORD:
            return new EditPrescriptionCommandParser().parse(arguments);

        case SearchPrescriptionCommand.COMMAND_WORD:
            return new SearchPrescriptionCommandParser().parse(arguments);

        case SelectPrescriptionCommand.COMMAND_WORD:
            return new SelectPrescriptionCommandParser().parse(arguments);

        case SortPrescriptionCommand.COMMAND_WORD:
            return new SortPrescriptionCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}




