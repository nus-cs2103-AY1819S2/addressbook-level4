package seedu.pdf.ui;

/*import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.pdf.testutil.TypicalPdfs.SAMPLE_PDF_1;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.pdf.model.pdf.Pdf;*/

public class BrowserPanelTest extends GuiUnitTest {
    /*private SimpleObjectProperty<Pdf> selectedPdf = new SimpleObjectProperty<>();
    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> browserPanel = new BrowserPanel(selectedPdf));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(BrowserPanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a pdf
        guiRobot.interact(() -> selectedPdf.set(SAMPLE_PDF_1));
        URL expectedPdfUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + SAMPLE_PDF_1.getName().getFullName()
                .replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPdfUrl, browserPanelHandle.getLoadedUrl());
    }*/
}
