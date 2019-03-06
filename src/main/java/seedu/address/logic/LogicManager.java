package seedu.address.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.StartCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.BrainTrainParser;
import seedu.address.logic.parser.QuizModeParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.quiz.QuizModel;
import seedu.address.quiz.commands.QuizCommand;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final QuizModel quizModel;
    private final CommandHistory history;
    private final BrainTrainParser addressBookParser;
    private final QuizModeParser quizModeParser;

    public LogicManager(Model model, QuizModel quizModel) {
        this.model = model;
        this.quizModel = quizModel;
        history = new CommandHistory();
        addressBookParser = new BrainTrainParser();
        quizModeParser = new QuizModeParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        try {
            Command command = null;
            if (quizModel.isDone()) {
                command = addressBookParser.parseCommand(commandText);
                commandResult = command.execute(model, history);
            } else {
                QuizCommand quizCommand = quizModeParser.parse(commandText);
                commandResult = quizCommand.execute(quizModel, history);
            }

            // very ugly way
            if (command instanceof StartCommand) {
                StartCommand startCommand = (StartCommand) command;
                commandResult = startCommand.executeActual(quizModel, history);
            }

        } finally {
            history.add(commandText);
        }

        return commandResult;
    }

    @Override
    public ObservableList<String> getHistory() {
        return history.getHistory();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
