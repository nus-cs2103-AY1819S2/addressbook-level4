package seedu.address.logic.parser;

import java.io.File;
import java.util.Optional;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * Parses parses users directory selection and returns the corresponding file object.
 */
class MoveGuiParser {

    private static final String DIRCHOOSER_TITLE = "Select Directory";

    private DirectoryChooser directoryChooser;

    /**
     * Default Constructor, initializes directory chooser.
     */
    MoveGuiParser() {
        this.directoryChooser = new DirectoryChooser();
        this.directoryChooser.setTitle(DIRCHOOSER_TITLE);
    }

    /**
     * Opens the DirectoryChooser UI to allow the user to select
     * a directory of choice.
     * @return File Object corresponding to chosen directory.
     */
    Optional<File> selectDirectory() {
        return Optional.ofNullable(this.directoryChooser.showDialog(new Stage()));
    }
}
