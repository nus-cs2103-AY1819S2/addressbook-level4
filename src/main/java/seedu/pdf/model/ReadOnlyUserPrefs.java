package seedu.pdf.model;

import java.nio.file.Path;

import seedu.pdf.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getPdfBookFilePath();

}
