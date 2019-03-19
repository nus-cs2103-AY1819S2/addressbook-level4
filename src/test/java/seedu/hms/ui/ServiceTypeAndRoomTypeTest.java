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

public class ServiceTypeAndRoomTypeTest extends GuiUnitTest {
    private SimpleObjectProperty<Customer> selectedCustomer = new SimpleObjectProperty<>();
    private ServiceTypeAndRoomType serviceTypeAndRoomType;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> serviceTypeAndRoomType = new ServiceTypeAndRoomType(selectedCustomer));
        uiPartRule.setUiPart(serviceTypeAndRoomType);

        browserPanelHandle = new BrowserPanelHandle(serviceTypeAndRoomType.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        assertEquals(ServiceTypeAndRoomType.DEFAULT_PAGE, browserPanelHandle.getLoadedUrl());

        // associated web page of a customer
        guiRobot.interact(() -> selectedCustomer.set(ALICE));
        URL expectedCustomerUrl = new URL(ServiceTypeAndRoomType.SEARCH_PAGE_URL
            + ALICE.getName().fullName.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedCustomerUrl, browserPanelHandle.getLoadedUrl());
    }
}
