package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final EmployeeListPanelHandle employeeListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final MainMenuHandle mainMenu;
    private final ProjectListPanelHandle projectListPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        employeeListPanel = new EmployeeListPanelHandle(getChildNode(EmployeeListPanelHandle.EMPLOYEE_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        projectListPanel = new ProjectListPanelHandle(getChildNode(ProjectListPanelHandle.PROJECT_LIST_VIEW_ID));
    }

    public EmployeeListPanelHandle getEmployeeListPanel() {
        return employeeListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public ProjectListPanelHandle getProjectListPanel() {
        return projectListPanel;
    }
}
