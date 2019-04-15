package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.util.ModuleTree;
import seedu.address.commons.util.Node;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.moduleinfo.ModuleInfoWorkload;

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
    private TreeView prerequisitetree;
    @FXML
    private TableView workloadtable;

    public DisplayModuleInfo(ModuleInfo module, int displayedIndex) {
        super(FXML);
        this.moduleInfo = module;
        id.setText(displayedIndex + ". ");
        moduleinfocode.setText(moduleInfo.getCodeString());
        moduleinfotitle.setText(moduleInfo.getTitleString());
        moduleinfodepartment.setText(moduleInfo.getDepartmentString());
        moduleinfocredits.setText(moduleInfo.getCreditString());
        moduleinfodescription.setText(moduleInfo.getDescriptionString());
        //moduleinfoprerequisites.setText(moduleInfo.getPrerequisitesString());
        //moduleinfoworkload.setText(moduleInfo.getWorkloadString());
        prerequisitetree.setRoot(generateTreeview());
        prerequisitetree.setShowRoot(false);
        generateWorkloadTableView();
    }

    /**
     * Creates Table for ModuleInfo Workload
     */
    public void generateWorkloadTableView() {
        ObservableList<ModuleInfoWorkload> list = FXCollections.observableArrayList();
        list.add(moduleInfo.getModuleInfoWorkload());
        workloadtable.setItems(list);

        TableColumn<ModuleInfoWorkload, Double> lecture = new TableColumn("Lecture");
        lecture.setCellValueFactory(new PropertyValueFactory<ModuleInfoWorkload, Double>("lecture"));
        TableColumn<ModuleInfoWorkload, Double> tutorial = new TableColumn("Tutorial");
        tutorial.setCellValueFactory(new PropertyValueFactory<ModuleInfoWorkload, Double>("tutorial"));
        TableColumn<ModuleInfoWorkload, Double> lab = new TableColumn("Lab");
        lab.setCellValueFactory(new PropertyValueFactory<ModuleInfoWorkload, Double>("lab"));
        TableColumn<ModuleInfoWorkload, Double> project = new TableColumn("Project");
        project.setCellValueFactory(new PropertyValueFactory<ModuleInfoWorkload, Double>("project"));
        TableColumn<ModuleInfoWorkload, Double> preparation = new TableColumn("Preparation");
        preparation.setCellValueFactory(new PropertyValueFactory<ModuleInfoWorkload, Double>("preparation"));

        workloadtable.getColumns().addAll(lecture, tutorial, lab, project, preparation);
    }

    /**
     * Method to create a root node of a TreeView and generates a full prerequisite Tree
     * @return root
     */
    public TreeItem<String> generateTreeview() {
        TreeItem<String> root = new TreeItem<>("Root");
        root.setExpanded(true);
        ModuleTree moduleTree = moduleInfo.getModuleInfoPrerequisite().getModuleTree();
        TreeItem<String> codeNode = new TreeItem<String>(moduleTree.getModuleCode());
        root.getChildren().add(codeNode);
        if (!moduleTree.getHead().getChildList().isEmpty()) {
            Node pesudoHead = moduleTree.getHead().getChildList().get(0);
            createMinorTree(pesudoHead, codeNode);
        }
        return root;
    }

    /**
     * Create a sub-branch of the prerequisite Tree so that it can be recursively called
     * @param pesudoHead
     * @param currHead
     */
    public void createMinorTree(Node pesudoHead, TreeItem<String> currHead) {
        if (pesudoHead.isModule()) {
            String code = pesudoHead.getValue();
            TreeItem<String> moduleitem = new TreeItem<>(code);
            currHead.getChildren().add(moduleitem);
        }

        if (!pesudoHead.isHead() && !pesudoHead.isModule()) {
            String operation = pesudoHead.getValue();
            if ("OR".equals(operation)) {
                operation = "One of :";
            }
            if ("AND".equals(operation)) {
                operation = "All of :";
            }
            TreeItem<String> operator = new TreeItem<>(operation);
            operator.setExpanded(true);
            currHead.getChildren().add(operator);

            for (int i = 0; i < pesudoHead.getChildList().size(); i++) {
                Node curr = pesudoHead.getChildList().get(i);
                createMinorTree(curr, operator);
            }

        }
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
