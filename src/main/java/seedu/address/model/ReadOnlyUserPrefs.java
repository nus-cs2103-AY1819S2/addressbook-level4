package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    /**
     * Returns an unmodifiable view of guiSettings.
     */
    GuiSettings getGuiSettings();

    /**
     * Returns an unmodifiable view of the menuFilePath.
     */
    Path getMenuFilePath();

    /**
     * Returns an unmodifiable view of the ordersFilePath.
     */
    Path getOrdersFilePath();

    /**
     * Returns an unmodifiable view of the tablesFilePath.
     */
    Path getTablesFilePath();

    /**
     * Returns an unmodifiable view of the statisticsFilePath.
     */
    Path getStatisticsFilePath();

}
