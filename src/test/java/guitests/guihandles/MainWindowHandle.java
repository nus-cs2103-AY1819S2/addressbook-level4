package guitests.guihandles;

import javafx.stage.Stage;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {

    private final FlashcardListPanelHandle flashcardListPanel;
    private final ResultDisplayHandle resultDisplay;
    private final CommandBoxHandle commandBox;
    private final StatusBarFooterHandle statusBarFooter;
    private final ToolbarHandle toolbar;
    private final CardViewHandle cardViewPanel;

    public MainWindowHandle(Stage stage) {
        super(stage);

        flashcardListPanel =
            new FlashcardListPanelHandle(getChildNode(FlashcardListPanelHandle.FLASHCARD_LIST_VIEW_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
        toolbar = new ToolbarHandle(getChildNode(ToolbarHandle.TOOLBAR_ID));
        cardViewPanel = new CardViewHandle(getChildNode(CardViewHandle.CARD_VIEW_ID));
    }

    public FlashcardListPanelHandle getFlashcardListPanel() {
        return flashcardListPanel;
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

    public ToolbarHandle getToolbar() {
        return toolbar;
    }

    public CardViewHandle getCardViewPanel() {
        return cardViewPanel;
    }
}
