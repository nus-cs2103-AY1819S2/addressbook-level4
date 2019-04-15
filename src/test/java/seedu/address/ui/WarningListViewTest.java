package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalMedicines.getTypicalMedicines;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysMedicine;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.WarningCardHandle;
import guitests.guihandles.WarningListViewHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.warning.WarningPanelPredicateAccessor;
import seedu.address.commons.util.warning.WarningPanelPredicateType;
import seedu.address.model.medicine.Company;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Quantity;


public class WarningListViewTest extends GuiUnitTest {
    private static final ObservableList<Medicine> TYPICAL_MEDICINES =
            FXCollections.observableList(getTypicalMedicines());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private WarningListViewHandle warningListViewHandle;

    @Test
    public void display_expiryListView() {
        initUi(TYPICAL_MEDICINES, WarningPanelPredicateType.EXPIRY);

        for (int i = 0; i < TYPICAL_MEDICINES.size(); i++) {
            warningListViewHandle.navigateToCard(TYPICAL_MEDICINES.get(i));
            Medicine expectedMedicine = TYPICAL_MEDICINES.get(i);
            WarningCardHandle actualCard = warningListViewHandle
                    .getWarningCardHandle(i, WarningPanelPredicateType.EXPIRY);

            assertCardDisplaysMedicine(expectedMedicine, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void display_lowStockListView() {
        initUi(TYPICAL_MEDICINES, WarningPanelPredicateType.LOW_STOCK);

        for (int i = 0; i < TYPICAL_MEDICINES.size(); i++) {
            warningListViewHandle.navigateToCard(TYPICAL_MEDICINES.get(i));
            Medicine expectedMedicine = TYPICAL_MEDICINES.get(i);
            WarningCardHandle actualCard = warningListViewHandle
                    .getWarningCardHandle(i, WarningPanelPredicateType.LOW_STOCK);

            assertCardDisplaysMedicine(expectedMedicine, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    /**
     * Verifies that creating and deleting large number of medicines in {@code WarningListView} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Medicine> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList, WarningPanelPredicateType.EXPIRY);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of medicine cards exceeded time limit");

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList, WarningPanelPredicateType.LOW_STOCK);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of medicine cards exceeded time limit");
    }

    /**
     * Returns a list of medicines containing {@code medicineCount} medicines that is used to populate the
     * {@code MedicineListPanel}.
     */
    private ObservableList<Medicine> createBackingList(int medicineCount) {
        ObservableList<Medicine> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < medicineCount; i++) {
            Name name = new Name(i + "a");
            Quantity quantity = new Quantity("0");
            Expiry expiry = new Expiry("-");
            Company company = new Company("a");
            Medicine medicine = new Medicine(name, company, quantity, expiry, Collections.emptySet(),
                    Collections.emptyMap());
            backingList.add(medicine);
        }
        return backingList;
    }

    /**
     * Initializes {@code warningListViewHandle} with a {@code WarningListView} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code WarningListView}.
     */
    private void initUi(ObservableList<Medicine> backingList, WarningPanelPredicateType listType) {
        WarningPanelPredicateAccessor predicateAccessor = new WarningPanelPredicateAccessor();
        predicateAccessor.setMaxThresholds();

        WarningListView warningListView =
                new WarningListView(backingList, listType, predicateAccessor);
        uiPartRule.setUiPart(warningListView);

        warningListViewHandle = new WarningListViewHandle(getChildNode(warningListView.getRoot(),
                WarningListViewHandle.WARNING_LIST_VIEW_ID));
    }
}
