package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.course.Course.MESSAGE_REQ_COMPLETED;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.recmodule.RecModule;

/**
 * Lists modules that the user is recommended to take based on passed modules and course requirements.
 */
public class RecCommand extends Command {

    public static final String COMMAND_WORD = "rec";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":Recommends a list of modules that can be taken "
            + "based on GradTrak modules and course requirements.";

    public static final String MESSAGE_REC = "Recommended modules found: %d";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateRecModuleList();
        ObservableList<RecModule> sortedList = model.getRecModuleListSorted();
        if (sortedList.isEmpty()) {
            return new CommandResult(MESSAGE_REQ_COMPLETED);
        }

        return new CommandResult(String.format(MESSAGE_REC, sortedList.size()));
    }

    /**
     * Generates a {@code String} representing a {@code List} of {@code RecModule}.
     * @param sortedList The {@code List} of {@code RecModule}.
     * @return The {@code String} stated above.
     */
    private static String generateResultString(ObservableList<RecModule> sortedList) {
        StringBuilder sb = new StringBuilder();
        for (RecModule recModule : sortedList) {
            assert (recModule.getCourseReqType().isPresent());
            sb.append(recModule.getModuleInfoCode().toString())
                    .append(" [").append(recModule.getCourseReqType().get().name()).append("]")
                    .append("\n");
        }

        return sb.toString();
    }
}
