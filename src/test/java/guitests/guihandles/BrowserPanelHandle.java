package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * A handler for the {@code BrowserPanel} of the UI.
 */
public class BrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#browser";
    public static final String CURRENT_QUESTION = "#question";

    private boolean isWebViewLoaded = true;

    private String lastRememberedQuestion;

    public BrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);

        WebView webView = getChildNode(BROWSER_ID);
        WebEngine engine = webView.getEngine();
        new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
            }
        }));
    }

    /**
     * @return the string form of the {@code Question} of the card currently in the {@code BrowserPanel}
     */
    public String getCurrentQuestion() {
        Label currentCardQuestion = getChildNode(CURRENT_QUESTION);
        return currentCardQuestion.getText();
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(BROWSER_ID));
    }

    /**
     * Remembers the {@code URL} of the currently loaded page.
     */
    public void rememberQuestion() {
        lastRememberedQuestion = getCurrentQuestion();
    }

    /**
     * Returns true if the current {@code URL} is different from the value remembered by the most recent
     * {@code rememberQuestion()} call.
     */
    public boolean isQuestionChanged() {
        return !lastRememberedQuestion.equals(getCurrentQuestion());
    }

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     */
    public boolean isLoaded() {
        return isWebViewLoaded;
    }
}
