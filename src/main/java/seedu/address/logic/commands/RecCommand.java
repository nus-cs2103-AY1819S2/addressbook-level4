package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.course.Course.MESSAGE_REQ_COMPLETED;

import java.util.HashMap;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Lists modules that the user is recommended to take based on completed modules and course requirements.
 */
public class RecCommand extends Command {

    public static final String COMMAND_WORD = "rec";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":Recommends a list of modules that can be taken "
            + "based on completed modules and course requirements.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        HashMap<ModuleInfoCode, CourseReqType> codeToReqMap = model.updateRecModuleList();

        return new CommandResult(generateResultString(model.getRecModuleListSorted(), codeToReqMap));
    }

    /**
     * Returns a String representing a list of module codes and the req types satisfied.
     * @param sortedList The List of ModuleInfoCode.
     * @param codeToReqMap The HashMap of ModuleInfoCode to CourseReqType.
     * @return a String.
     */
    private static String generateResultString(ObservableList<ModuleInfoCode> sortedList,
                                               HashMap<ModuleInfoCode, CourseReqType> codeToReqMap) {
        if (sortedList.isEmpty()) {
            return MESSAGE_REQ_COMPLETED;
        }

        StringBuilder sb = new StringBuilder().append("Recommended modules:\n");
        for (ModuleInfoCode moduleInfoCode : sortedList) {
            sb.append(moduleInfoCode.toString())
                    .append(" [").append(codeToReqMap.get(moduleInfoCode).name()).append("]")
                    .append("\n");
        }

        return sb.toString();
    }
}
