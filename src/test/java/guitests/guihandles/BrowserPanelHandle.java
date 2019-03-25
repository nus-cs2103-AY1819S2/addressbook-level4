package guitests.guihandles;

import java.net.URL;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#browser";
    public static final String CURRENT_CARD_QUESTION = "#cardQuestion";

    private boolean isWebViewLoaded = true;

    private String lastRememberedQuestion;

    public BrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);
    }

    /**
     * @return the string form of the {@code Question} of the folder currently in the {@code BrowserPanel}
     */
    public String getCurrentQuestion() {
        Label currentCardQuestion = getChildNode(CURRENT_CARD_QUESTION);
        return currentCardQuestion.getText();
    }

    /**
     * Returns the question of the currently loaded folder.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(BROWSER_ID));
    }

    /**
     * Remembers the question of the currently loaded folder.
     */
    public void rememberQuestion() {
        lastRememberedQuestion = getCurrentQuestion();
    }

    /**
     * Returns true if the current question is different from the value remembered by the most recent
     * {@code rememberQuestion()} call.
     */
    public boolean isQuestionChanged() {
        return !lastRememberedQuestion.equals(getCurrentQuestion());
    }

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     */
    public boolean isLoaded() {
        getChildNode(CURRENT_CARD_QUESTION).applyCss();
        return isWebViewLoaded;
    }
}
