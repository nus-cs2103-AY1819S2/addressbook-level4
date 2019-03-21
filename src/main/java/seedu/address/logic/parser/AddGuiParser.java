package seedu.address.logic.parser;

import java.io.File;
import java.util.Optional;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Parses parses user selection and returns the corresponding file object.
 */
class AddGuiParser {

    private static final String FILECHOOSER_TITLE = "Select PDF";
    private static final String FILECHOOSER_FILTER_DESCRIPTION = "PDF Files";
    private static final String FILECHOOSER_FILTER_VALUE = "*.pdf";
    private FileChooser fileChooser;

    /**
     * Default Constructor, initializes file chooser.
     */
    AddGuiParser() {
        this.fileChooser = new FileChooser();
        this.fileChooser.setTitle(AddGuiParser.FILECHOOSER_TITLE);
        this.fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter(AddGuiParser.FILECHOOSER_FILTER_DESCRIPTION,
                        AddGuiParser.FILECHOOSER_FILTER_VALUE));

    }

    /**
     * Opens the FileChooser UI to allow the user to select
     * a Pdf file of choice.
     * @return File Object corresponding to chosen PDF file.
     */
    Optional<File> selectPDF() {
        return Optional.ofNullable(fileChooser.showOpenDialog(new Stage()));
    }

}
