package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.recmodule.RecModule;

/**
 * UI component that displays information of a {@code RecModule}.
 */
public class DisplayRecModule extends UiPart<Region> {

    private static final String FXML = "DisplayRecModule.fxml";

    public final RecModule recModule;

    @FXML
    private HBox recPane;
    @FXML
    private Label index;
    @FXML
    private Label moduleInfoCode;
    @FXML
    private Label moduleInfoTitle;
    @FXML
    private Label courseReqType;

    public DisplayRecModule(RecModule recModule, int id) {
        super(FXML);
        requireNonNull(recModule);
        assert recModule.getCourseReqType().isPresent();
        this.recModule = recModule;

        index.setText(id + ". ");
        moduleInfoCode.setText(recModule.getCode().toString());
        moduleInfoTitle.setText(recModule.getModuleInfo().getModuleInfoTitle().toString());
        courseReqType.setText(recModule.getCourseReqType().get().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DisplayModuleInfo)) {
            return false;
        }

        // state check
        DisplayRecModule displayRecModule = (DisplayRecModule) other;
        return recModule.equals(displayRecModule.recModule);
    }
}

