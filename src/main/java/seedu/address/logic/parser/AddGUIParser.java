package seedu.address.logic.parser;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class AddGUIParser {

    private static final String FILECHOOSER_TITLE = "Select PDF";
    private static final String FILECHOOSER_FILTER_DESCRIPTION = "PDF Files";
    private static final String FILECHOOSER_FILTER_VALUE = "*.pdf";
    private FileChooser fileChooser;

    public AddGUIParser() {
        this.fileChooser = new FileChooser();
        this.fileChooser.setTitle(AddGUIParser.FILECHOOSER_TITLE);
        this.fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter(AddGUIParser.FILECHOOSER_FILTER_DESCRIPTION, 
                        AddGUIParser.FILECHOOSER_FILTER_VALUE));

    }

    public Optional<File> selectPDF() {
        return Optional.ofNullable(fileChooser.showOpenDialog(new Stage()));
    }

}
