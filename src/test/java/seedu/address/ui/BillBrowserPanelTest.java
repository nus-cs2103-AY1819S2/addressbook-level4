//package seedu.address.ui;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import guitests.guihandles.BrowserPanelHandle;
//import javafx.beans.property.SimpleObjectProperty;
//import seedu.address.model.statistics.Bill;
//
//public class BillBrowserPanelTest extends GuiUnitTest {
//    private SimpleObjectProperty<Bill> selectedBill = new SimpleObjectProperty<>();
//    private BillBrowserPanel billBrowserPanel;
//    private BrowserPanelHandle billBrowserPanelHandle;
//
//    @Before
//    public void setUp() {
//        guiRobot.interact(() -> billBrowserPanel = new BillBrowserPanel(selectedBill));
//        uiPartRule.setUiPart(billBrowserPanel);
//
//        billBrowserPanelHandle = new BrowserPanelHandle(billBrowserPanel.getRoot());
//    }
//
//    @Test
//    public void display() throws Exception {
//        // default web page
//        assertEquals(BillBrowserPanel.DEFAULT_PAGE, billBrowserPanelHandle.getLoadedUrl());
///**
//        // associated web page of a bill
//        guiRobot.interact(() -> selectedBill.set(ALICE));
//        URL expectedPersonUrl = new URL(BillBrowserPanel.SEARCH_PAGE_URL + ALICE.getName().fullName
//                .replaceAll(" ", "%20"));
//
//        waitUntilBrowserLoaded(billBrowserPanelHandle);
//        assertEquals(expectedPersonUrl, billBrowserPanelHandle.getLoadedUrl());
// **/
//    }
//}
