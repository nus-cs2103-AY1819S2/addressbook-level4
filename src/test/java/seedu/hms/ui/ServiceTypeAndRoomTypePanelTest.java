package seedu.hms.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.hms.testutil.TypicalCustomers.ALICE;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import seedu.hms.model.customer.Customer;

public class ServiceTypeAndRoomTypePanelTest extends GuiUnitTest {
    private SimpleObjectProperty<Customer> selectedCustomer = new SimpleObjectProperty<>();
    private ServiceTypeAndRoomTypePanel serviceTypeAndRoomTypePanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> serviceTypeAndRoomTypePanel = new ServiceTypeAndRoomTypePanel(selectedCustomer));
        uiPartRule.setUiPart(serviceTypeAndRoomTypePanel);

        browserPanelHandle = new BrowserPanelHandle(serviceTypeAndRoomTypePanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(ServiceTypeAndRoomTypePanel.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a customer
        guiRobot.interact(() -> selectedCustomer.set(ALICE));
        URL expectedCustomerUrl = new URL(ServiceTypeAndRoomTypePanel.SEARCH_PAGE_URL
            + ALICE.getName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedCustomerUrl, browserPanelHandle.getLoadedUrl());
    }
}
