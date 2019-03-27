package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * An UI component that displays information of a {@code ModuleInfo}.
 */
public class DisplayModuleInfo extends UiPart<Region> {

    private static final String FXML = "DisplayModuleInfo.fxml";

    public final ModuleInfo moduleInfo;

    @FXML
    private HBox moduleinfopane;
    @FXML
    private Label id;
    @FXML
    private Label moduleinfocode;
    @FXML
    private Label moduleinfotitle;
    @FXML
    private Label moduleinfodepartment;
    @FXML
    private Label moduleinfocredits;
    @FXML
    private Label moduleinfodescription;
    @FXML
    private Label moduleinfoprerequisites;
    @FXML
    private Label moduleinfoworkload;

    public DisplayModuleInfo(ModuleInfo module, int displayedIndex) {
        super(FXML);
        this.moduleInfo = module;
        id.setText(displayedIndex + ". ");
        moduleinfocode.setText(moduleInfo.getCodeString());
        moduleinfotitle.setText(moduleInfo.getTitleString());
        moduleinfodepartment.setText(moduleInfo.getDepartmentString());
        moduleinfocredits.setText(moduleInfo.getCreditString());
        moduleinfodescription.setText(moduleInfo.getDescriptionString());
        moduleinfoprerequisites.setText(moduleInfo.getPrerequisitesString());
        moduleinfoworkload.setText(moduleInfo.getWorkloadString());
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
        DisplayModuleInfo displayModuleInfo = (DisplayModuleInfo) other;
        return id.getText().equals(displayModuleInfo.moduleinfocode.getText())
                && displayModuleInfo.equals(displayModuleInfo.moduleInfo);
    }


}
