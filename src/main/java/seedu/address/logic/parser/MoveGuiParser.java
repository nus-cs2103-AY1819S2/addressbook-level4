package seedu.address.logic.parser;

import java.io.File;
import java.util.Optional;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

class MoveGuiParser {

    private static final String DIRCHOOSER_TITLE = "Select Directory";

    private DirectoryChooser directoryChooser;

    MoveGuiParser() {
        this.directoryChooser = new DirectoryChooser();
        this.directoryChooser.setTitle(DIRCHOOSER_TITLE);
    }

    Optional<File> selectDirectory() {
        return Optional.ofNullable(this.directoryChooser.showDialog(new Stage()));
    }
}
