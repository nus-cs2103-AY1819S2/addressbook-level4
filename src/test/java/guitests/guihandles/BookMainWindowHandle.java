package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class BookMainWindowHandle extends StageHandle {

    private final BookListPanelHandle bookListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final BookCommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final BookMainMenuHandle mainMenu;
    private final BookBrowserPanelHandle browserPanel;

    public BookMainWindowHandle(Stage stage) {
        super(stage);

        bookListPanel = new BookListPanelHandle(getChildNode(BookListPanelHandle.BOOK_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new BookCommandBoxHandle(getChildNode(BookCommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        mainMenu = new BookMainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        browserPanel = new BookBrowserPanelHandle(getChildNode(BookBrowserPanelHandle.BROWSER_ID));
    }

    public BookListPanelHandle getBookListPanel() {
        return bookListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public BookCommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public BookMainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public BookBrowserPanelHandle getBrowserPanel() {
        return browserPanel;
    }
}
