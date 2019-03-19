package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalTables;

import org.junit.Test;

import guitests.guihandles.TablesFlowPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import seedu.address.model.table.Table;
import seedu.address.testutil.TableBuilder;

public class TableFlowPanelTest extends GuiUnitTest {
    private static final ObservableList<Table> TYPICAL_TABLES =
            FXCollections.observableList(getTypicalTables());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Table> selectedTable = new SimpleObjectProperty<>();
    private TablesFlowPanelHandle tablesFlowPanelHandle;

    /**
     * Verifies that creating and deleting large number of persons in {@code PersonListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Table> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of person cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code PersonListPanel}.
     */
    private ObservableList<Table> createBackingList(int tableCount) {
        ObservableList<Table> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < tableCount; i++) {
            Table table = new TableBuilder().withTableNumber(String.valueOf(i + 1)).build();
            backingList.add(table);
        }
        return backingList;
    }

    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<Table> backingList) {
        TableFlowPanel tableFlowPanel = new TableFlowPanel(backingList, new ScrollPane());
        uiPartRule.setUiPart(tableFlowPanel);

        tablesFlowPanelHandle = new TablesFlowPanelHandle(getChildNode(tableFlowPanel.getRoot(),
                TablesFlowPanelHandle.TABLES_FLOW_PANEL_ID));
    }
}
