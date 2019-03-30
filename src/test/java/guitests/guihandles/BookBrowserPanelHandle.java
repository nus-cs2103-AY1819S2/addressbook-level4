package guitests.guihandles;

import java.net.URL;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * A handler for the {@code BookBrowserPanel} of the UI.
 */
public class BookBrowserPanelHandle extends NodeHandle<Node> {

    public static final String BROWSER_ID = "#reviewMessage";
    private static final String MESSAGE_FIELD_ID = "#reviewMessage";

    private URL lastRememberedUrl;
    private boolean isWebViewLoaded = true;
    private final Label messageLabel;

    public BookBrowserPanelHandle(Node browserPanelNode) {
        super(browserPanelNode);

        messageLabel = getChildNode(MESSAGE_FIELD_ID);
    }

    public String getMessageField() {
        return messageLabel.getText();
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
    public void rememberUrl() {
        lastRememberedUrl = getLoadedUrl();
    }

    /**
     * Returns true if the current {@code URL} is different from the value remembered by the most recent
     * {@code rememberUrl()} call.
     */
    public boolean isUrlChanged() {
        return false;
    }

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     */
    public boolean isLoaded() {
        return isWebViewLoaded;
    }
}
