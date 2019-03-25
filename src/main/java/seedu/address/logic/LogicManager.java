package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.quiz.QuizStartCommand;
import seedu.address.logic.parser.ManagementModeParser;
import seedu.address.logic.parser.QuizModeParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelmanager.QuizModel;
import seedu.address.model.modelmanager.management.ManagementModel;
import seedu.address.model.quiz.QuizUiDisplayFormatter;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Storage storageManager;
    private final ManagementModel managementModel;
    private final QuizModel quizModel;
    private final CommandHistory history;
    private final ManagementModeParser managementModeParser;
    private final QuizModeParser quizModeParser;

    /**
     * Different mode that will show different UI and have access to different commands.
     */
    public enum Mode {
        MANAGEMENT,
        QUIZ
    }

    public LogicManager(ManagementModel managementModel, QuizModel quizModel, Storage storageManager) {
        this.storageManager = storageManager;
        this.managementModel = managementModel;
        this.quizModel = quizModel;
        history = new CommandHistory();
        managementModeParser = new ManagementModeParser();
        quizModeParser = new QuizModeParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        try {
            Command command;
            if (getMode() == Mode.MANAGEMENT) {
                command = managementModeParser.parse(commandText);
                commandResult = command.execute(managementModel, history);
            } else {
                command = quizModeParser.parse(commandText);
                commandResult = command.execute(quizModel, history);
            }

            if (command instanceof QuizStartCommand) {
                QuizStartCommand quizStartCommand = (QuizStartCommand) command;
                commandResult = quizStartCommand.executeActual(quizModel, history);
            }
        } finally {
            history.add(commandText);
        }

        return commandResult;
    }

    @Override
    public Mode getMode() {
        return quizModel.isQuizDone() ? Mode.MANAGEMENT : Mode.QUIZ;
    }

    @Override
    public QuizUiDisplayFormatter getDisplayFormatter() {
        return quizModel.getDisplayFormatter();
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return managementModel.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        managementModel.setGuiSettings(guiSettings);
    }
}
