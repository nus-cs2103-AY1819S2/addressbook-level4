package seedu.address.testutil;

import seedu.address.commons.util.FileName;
import seedu.address.logic.commands.ExportCommand;

/**
 * A utility class for Export.
 */
public class ExportUtil {

    /**
     * Returns an export command string for exporting the current list to a {@code fileName}.csv file.
     */
    public static String getExportCommand(FileName fileName) {
        return ExportCommand.COMMAND_WORD + " " + fileName;
    }

}
