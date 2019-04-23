package guitests.guihandles;

import java.io.StringWriter;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import guitests.GuiRobot;

import javafx.concurrent.Worker;
import javafx.scene.Node;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * A handler for the {@code InfoPanel} of the UI.
 * @author Hui Chun
 */
public class InfoPanelHandle extends NodeHandle<Node> {

    public static final String WEB_VIEW_ID = "#webView";

    private boolean isWebViewLoaded = true;
    private String loadedContent = "";
    private String lastRememberedContent = "";
    private URL lastRememberedUrl;

    public InfoPanelHandle(Node infoPanelNode) {
        super(infoPanelNode);

        WebView webView = getChildNode(WEB_VIEW_ID);
        WebEngine engine = webView.getEngine();
        new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
                Document doc = engine.getDocument();
                setLoadedContent(doc);
            }
        }));
    }

    /**
     * A setter method to set the loaded content string variable that will be generated
     * from transforming the document object model (DOM) source.
     * @param doc DOM object from web engine
     */
    private void setLoadedContent(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            this.loadedContent = writer.toString();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the loaded page content.
     * @return the loaded content in the web view
     */
    public String getLoadedContent() {
        return this.loadedContent;
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(WEB_VIEW_ID));
    }

    /**
     * Remembers the {@code String} of the loaded content.
     */
    public void rememberContent() {
        lastRememberedContent = getLoadedContent();
    }

    /**
     * Remembers the {@code URL} of the currently loaded page.
     */
    public void rememberUrl() {
        lastRememberedUrl = getLoadedUrl();
    }

    /**
     * Returns true if the current {@code content} is different from the value remembered by the most recent
     * {@code rememberContent()} call.
     */
    public boolean isLoadedContentChanged() {
        return !lastRememberedContent.equals(getLoadedContent());
    }

    /**
     * Returns true if the current {@code URL} is different from the value remembered by the most recent
     * {@code rememberUrl()} call.
     */
    public boolean isUrlChanged() {
        return !lastRememberedUrl.equals(getLoadedUrl());
    }

    /**
     * Returns true if the browser is done loading a page, or if this browser has yet to load any page.
     */
    public boolean isLoaded() {
        return isWebViewLoaded;
    }
}
