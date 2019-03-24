package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddAppCommand;
import seedu.address.logic.commands.AddDirectoryCommand;
import seedu.address.logic.commands.AddMedicineCommand;
import seedu.address.logic.commands.AddPatientCommand;
import seedu.address.logic.commands.AddRemCommand;
import seedu.address.logic.commands.AlarmCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.ConsultationCommand;
import seedu.address.logic.commands.DeleteAppCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePatientCommand;
import seedu.address.logic.commands.DiagnosePatientCommand;
import seedu.address.logic.commands.EditPatientCommand;
import seedu.address.logic.commands.EndConsultationCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListAppCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListConsultationCommand;
import seedu.address.logic.commands.ListPatientCommand;
import seedu.address.logic.commands.ListRemCommand;
import seedu.address.logic.commands.PrescriptionCommand;
import seedu.address.logic.commands.PurchaseMedicineCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SetConsultationFeeCommand;
import seedu.address.logic.commands.SetPriceCommand;
import seedu.address.logic.commands.StatisticsCommand;
import seedu.address.logic.commands.ViewStorageCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddPatientCommand.COMMAND_WORD:
        case AddPatientCommand.COMMAND_ALIAS:
            return new AddPatientParser().parse(arguments);

        case AddAppCommand.COMMAND_WORD:
            return new AddAppCommandParser().parse(arguments);

        case DeleteAppCommand.COMMAND_WORD:
            return new DeleteAppCommandParser().parse(arguments);

        case ListAppCommand.COMMAND_WORD:
            return new ListAppCommand();

        case AddRemCommand.COMMAND_WORD:
            return new AddRemCommandParser().parse(arguments);

        case ListRemCommand.COMMAND_WORD:
            return new ListRemCommand();

        case EditPatientCommand.COMMAND_WORD:
            return new EditPatientParser().parse(arguments);

        case ListPatientCommand.COMMAND_WORD:
            return new ListPatientParser().parse(arguments);

        case ConsultationCommand.COMMAND_WORD:
            return new ConsultationCommandParser().parse(arguments);

        case DiagnosePatientCommand.COMMAND_WORD:
            return new DiagnosePatientCommandParser().parse(arguments);

        case StatisticsCommand.COMMAND_WORD:
        case StatisticsCommand.COMMAND_ALIAS:
            return new StatisticsCommandParser().parse(arguments);

        case SetConsultationFeeCommand.COMMAND_WORD:
        case SetConsultationFeeCommand.COMMAND_ALIAS:
            return new SetConsultationFeeCommandParser().parse(arguments);

        case PrescriptionCommand.COMMAND_WORD:
            return new PrescriptionCommandParser().parse(arguments);

        case EndConsultationCommand.COMMAND_WORD:
            return new EndConsultationCommand();

        case ListConsultationCommand.ALIAS_WORD:
        case ListConsultationCommand.COMMAND_WORD:
            return new ListConsultationCommandParser().parse(arguments);

        case AddMedicineCommand.COMMAND_WORD:
            return new AddMedicineCommandParser().parse(arguments);

        case ViewStorageCommand.COMMAND_WORD:
            return new ViewStorageCommandParser().parse(arguments);

        case AlarmCommand.COMMAND_WORD:
            return new AlarmCommandParser().parse(arguments);

        case AddDirectoryCommand.COMMAND_WORD:
            return new AddDirectoryCommandParser().parse(arguments);

        case PurchaseMedicineCommand.COMMAND_WORD:
            return new PurchaseMedicineCommandParser().parse(arguments);

        case SetPriceCommand.COMMAND_WORD:
            return new SetPriceCommandParser().parse(arguments);

        case DeletePatientCommand.COMMAND_WORD:
        case DeletePatientCommand.COMMAND_ALIAS:
            return new DeletePatientParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
