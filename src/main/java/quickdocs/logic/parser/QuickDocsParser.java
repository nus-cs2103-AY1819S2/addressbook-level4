package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static quickdocs.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quickdocs.logic.commands.AbortConsultationCommand;
import quickdocs.logic.commands.AddAppCommand;
import quickdocs.logic.commands.AddDirectoryCommand;
import quickdocs.logic.commands.AddMedicineCommand;
import quickdocs.logic.commands.AddPatientCommand;
import quickdocs.logic.commands.AddRemCommand;
import quickdocs.logic.commands.AlarmCommand;
import quickdocs.logic.commands.Command;
import quickdocs.logic.commands.ConsultationCommand;
import quickdocs.logic.commands.DeleteAppCommand;
import quickdocs.logic.commands.DeletePatientCommand;
import quickdocs.logic.commands.DeleteRemCommand;
import quickdocs.logic.commands.DiagnosePatientCommand;
import quickdocs.logic.commands.EditPatientCommand;
import quickdocs.logic.commands.EndConsultationCommand;
import quickdocs.logic.commands.ExitCommand;
import quickdocs.logic.commands.FreeAppCommand;
import quickdocs.logic.commands.HelpCommand;
import quickdocs.logic.commands.HistoryCommand;
import quickdocs.logic.commands.ListAppCommand;
import quickdocs.logic.commands.ListConsultationCommand;
import quickdocs.logic.commands.ListPatientCommand;
import quickdocs.logic.commands.ListRemCommand;
import quickdocs.logic.commands.PrescriptionCommand;
import quickdocs.logic.commands.PurchaseMedicineCommand;
import quickdocs.logic.commands.SetConsultationFeeCommand;
import quickdocs.logic.commands.SetPriceCommand;
import quickdocs.logic.commands.StatisticsCommand;
import quickdocs.logic.commands.ViewStorageCommand;
import quickdocs.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class QuickDocsParser {

    /**
     * Used for initial separation of command word and args.
     */
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

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
        case AddAppCommand.COMMAND_ALIAS:
            return new AddAppCommandParser().parse(arguments);

        case DeleteAppCommand.COMMAND_WORD:
        case DeleteAppCommand.COMMAND_ALIAS:
            return new DeleteAppCommandParser().parse(arguments);

        case ListAppCommand.COMMAND_WORD:
        case ListAppCommand.COMMAND_ALIAS:
            return new ListAppCommandParser().parse(arguments);

        case FreeAppCommand.COMMAND_WORD:
        case FreeAppCommand.COMMAND_ALIAS:
            return new FreeAppCommandParser().parse(arguments);

        case AddRemCommand.COMMAND_WORD:
        case AddRemCommand.COMMAND_ALIAS:
            return new AddRemCommandParser().parse(arguments);

        case DeleteRemCommand.COMMAND_WORD:
        case DeleteRemCommand.COMMAND_ALIAS:
            return new DeleteRemCommandParser().parse(arguments);

        case ListRemCommand.COMMAND_WORD:
        case ListRemCommand.COMMAND_ALIAS:
            return new ListRemCommandParser().parse(arguments);

        case EditPatientCommand.COMMAND_WORD:
        case EditPatientCommand.COMMAND_ALIAS:
            return new EditPatientParser().parse(arguments);

        case ListPatientCommand.COMMAND_WORD:
        case ListPatientCommand.COMMAND_ALIAS:
            return new ListPatientParser().parse(arguments);

        case ConsultationCommand.COMMAND_WORD:
        case ConsultationCommand.COMMAND_ALIAS:
            return new ConsultationCommandParser().parse(arguments);

        case DiagnosePatientCommand.COMMAND_WORD:
        case DiagnosePatientCommand.COMMAND_ALIAS:
            return new DiagnosePatientCommandParser().parse(arguments);

        case StatisticsCommand.COMMAND_WORD:
        case StatisticsCommand.COMMAND_ALIAS:
            return new StatisticsCommandParser().parse(arguments);

        case SetConsultationFeeCommand.COMMAND_WORD:
        case SetConsultationFeeCommand.COMMAND_ALIAS:
            return new SetConsultationFeeCommandParser().parse(arguments);

        case PrescriptionCommand.COMMAND_WORD:
        case PrescriptionCommand.COMMAND_ALIAS:
            return new PrescriptionCommandParser().parse(arguments);

        case EndConsultationCommand.COMMAND_WORD:
        case EndConsultationCommand.COMMAND_ALIAS:
            return new EndConsultationCommand();

        case ListConsultationCommand.COMMAND_WORD:
        case ListConsultationCommand.COMMAND_ALIAS:
            return new ListConsultationCommandParser().parse(arguments);

        case AddMedicineCommand.COMMAND_WORD:
        case AddMedicineCommand.COMMAND_ALIAS:
            return new AddMedicineCommandParser().parse(arguments);

        case ViewStorageCommand.COMMAND_WORD:
        case ViewStorageCommand.COMMAND_ALIAS:
            return new ViewStorageCommandParser().parse(arguments);

        case AlarmCommand.COMMAND_WORD:
            return new AlarmCommandParser().parse(arguments);

        case AddDirectoryCommand.COMMAND_WORD:
        case AddDirectoryCommand.COMMAND_ALIAS:
            return new AddDirectoryCommandParser().parse(arguments);

        case PurchaseMedicineCommand.COMMAND_WORD:
        case PurchaseMedicineCommand.COMMAND_ALIAS:
            return new PurchaseMedicineCommandParser().parse(arguments);

        case SetPriceCommand.COMMAND_WORD:
        case SetPriceCommand.COMMAND_ALIAS:
            return new SetPriceCommandParser().parse(arguments);

        case DeletePatientCommand.COMMAND_WORD:
        case DeletePatientCommand.COMMAND_ALIAS:
            return new DeletePatientParser().parse(arguments);

        case AbortConsultationCommand.COMMAND_WORD:
        case AbortConsultationCommand.COMMAND_ALIAS:
            return new AbortConsultationCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * To judge whether suggestion mode should be turned on.
     * @param rawArgs The user input
     * @return whether suggestion mode should be turned on
     */
    public boolean isDirectoryFormat(String rawArgs) {
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(rawArgs);
        if (!matcher.matches()) {
            return false;
        }
        String commandWord = matcher.group("commandWord").trim();
        String arguments = matcher.group("arguments").trim();
        if (!arguments.contains("\\") || arguments.contains(" ")) {
            return false;
        }
        switch (commandWord) {
        case PurchaseMedicineCommand.COMMAND_WORD:
        case PurchaseMedicineCommand.COMMAND_ALIAS:
        case SetPriceCommand.COMMAND_WORD:
        case SetPriceCommand.COMMAND_ALIAS:
        case AlarmCommand.COMMAND_WORD:
        case ViewStorageCommand.COMMAND_WORD:
        case ViewStorageCommand.COMMAND_ALIAS:
        case AddMedicineCommand.COMMAND_WORD:
        case AddMedicineCommand.COMMAND_ALIAS:
        case AddDirectoryCommand.COMMAND_WORD:
        case AddDirectoryCommand.COMMAND_ALIAS:
            return true;
        default:
            return false;
        }
    }

    /**
     * To judge whether medicine is allowed as input for the command entered.
     * @param rawArgs The user input
     * @return whether suggestion mode should be turned on
     */
    public boolean isMedicineAllowed(String rawArgs) {
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(rawArgs);
        if (!matcher.matches()) {
            return false;
        }
        String commandWord = matcher.group("commandWord").trim();
        String arguments = matcher.group("arguments").trim();
        if (!arguments.contains("\\") || arguments.contains(" ")) {
            return false;
        }
        switch (commandWord) {
        case PurchaseMedicineCommand.COMMAND_WORD:
        case PurchaseMedicineCommand.COMMAND_ALIAS:
        case SetPriceCommand.COMMAND_WORD:
        case SetPriceCommand.COMMAND_ALIAS:
        case AlarmCommand.COMMAND_WORD:
        case ViewStorageCommand.COMMAND_WORD:
        case ViewStorageCommand.COMMAND_ALIAS:
            return true;
        default:
            return false;
        }
    }

    public String getArgument(String rawArgs) {
        rawArgs = rawArgs.trim();
        Matcher matcher = BASIC_COMMAND_FORMAT.matcher(rawArgs);
        if (!matcher.matches()) {
            throw new IllegalStateException(MESSAGE_INVALID_COMMAND_FORMAT);
        }
        return matcher.group("arguments").trim();
    }
}
