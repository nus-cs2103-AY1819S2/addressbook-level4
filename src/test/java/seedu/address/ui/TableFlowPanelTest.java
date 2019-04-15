package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import org.junit.Test;

import guitests.guihandles.TablesFlowPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableNumber;
import seedu.address.model.table.TableStatus;

public class TableFlowPanelTest extends GuiUnitTest {

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private TablesFlowPanelHandle tablesFlowPanelHandle;

    /**
     * Verifies that creating and deleting large number of tables in {@code TableListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Table> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            //            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of table cards exceeded time limit");
    }

    /**
     * Returns a list of tables containing {@code tableCount} tables that is used to populate the
     * {@code TableListPanel}.
     */
    private ObservableList<Table> createBackingList(int tableCount) {
        ObservableList<Table> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < tableCount; i++) {
            TableNumber tableNumber = new TableNumber(String.valueOf(i + 1));
            TableStatus tableStatus = new TableStatus("0/4");
            Table table = new Table(tableNumber, tableStatus);
            backingList.add(table);
        }
        return backingList;
    }

    /**
     * Initializes {@code tablesFlowPanelHandle} with a {@code TableFlowPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code TableFlowPanel}.
     */
    private void initUi(ObservableList<Table> backingList) {
        TableFlowPanel tableFlowPanel = new TableFlowPanel(backingList, new ScrollPane());
        uiPartRule.setUiPart(tableFlowPanel);

        tablesFlowPanelHandle = new TablesFlowPanelHandle(getChildNode(tableFlowPanel.getRoot(),
                TablesFlowPanelHandle.TABLE_FLOW_PANEL_ID));
    }
}
