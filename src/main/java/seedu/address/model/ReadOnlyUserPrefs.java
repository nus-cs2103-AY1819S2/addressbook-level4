package seedu.address.model;

import java.nio.file.Path;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.InformationPanelSettings;
import seedu.address.commons.core.WarningPanelSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getInventoryFilePath();

    WarningPanelSettings getWarningPanelSettings();

    InformationPanelSettings getInformationPanelSettings();

}
