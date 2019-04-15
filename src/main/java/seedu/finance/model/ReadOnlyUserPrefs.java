package seedu.finance.model;

import java.nio.file.Path;

import seedu.finance.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getFinanceTrackerFilePath();

}
