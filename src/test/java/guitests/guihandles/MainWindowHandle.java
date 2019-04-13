package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final LessonListPanelHandle lessonListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final MainMenuHandle mainMenu;
    private final MainPanelHandle mainPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        lessonListPanel = new LessonListPanelHandle(getChildNode(LessonListPanelHandle.LESSON_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        mainPanel = new MainPanelHandle(getChildNode(MainPanelHandle.MAIN_PANEL_ID));
    }

    public LessonListPanelHandle getLessonListPanel() {
        return lessonListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public MainPanelHandle getMainPanel() {
        return mainPanel;
    }
}
