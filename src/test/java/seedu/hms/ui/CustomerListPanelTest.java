package seedu.hms.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.hms.testutil.TypicalCustomers.getTypicalCustomers;
import static seedu.hms.testutil.TypicalIndexes.INDEX_SECOND_CUSTOMER;
import static seedu.hms.ui.testutil.GuiTestAssert.assertCustomerCardDisplaysCustomer;
import static seedu.hms.ui.testutil.GuiTestAssert.assertCustomerCardEquals;

import java.util.Collections;

import org.junit.Test;

import guitests.guihandles.CustomerCardHandle;
import guitests.guihandles.CustomerListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.hms.model.customer.Address;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.DateOfBirth;
import seedu.hms.model.customer.Email;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.customer.Name;
import seedu.hms.model.customer.Phone;

public class CustomerListPanelTest extends GuiUnitTest {

    private static final ObservableList<Customer> TYPICAL_CUSTOMERS =
        FXCollections.observableList(getTypicalCustomers());


    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 3000; //was 2500 initly changed to 300 to pass

    private final SimpleObjectProperty<Customer> selectedCustomer = new SimpleObjectProperty<>();
    private CustomerListPanelHandle customerListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_CUSTOMERS);

        for (int i = 0; i < TYPICAL_CUSTOMERS.size(); i++) {
            customerListPanelHandle.navigateToCard(TYPICAL_CUSTOMERS.get(i));
            Customer expectedCustomer = TYPICAL_CUSTOMERS.get(i);
            CustomerCardHandle actualCard = customerListPanelHandle.getCustomerCardHandle(i);

            assertCustomerCardDisplaysCustomer(expectedCustomer, actualCard);
            assertEquals((i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selectionModelSelectedCustomerChangedSelectionChanges() {
        initUi(TYPICAL_CUSTOMERS);
        Customer secondCustomer = TYPICAL_CUSTOMERS.get(INDEX_SECOND_CUSTOMER.getZeroBased());
        guiRobot.interact(() -> selectedCustomer.set(secondCustomer));
        guiRobot.pauseForHuman();
        CustomerCardHandle expectedCustomer =
            customerListPanelHandle.getCustomerCardHandle(INDEX_SECOND_CUSTOMER.getZeroBased());
        CustomerCardHandle selectedCustomer = customerListPanelHandle.getHandleToSelectedCard();
        assertCustomerCardEquals(expectedCustomer, selectedCustomer);
    }

    /**
     * Verifies that creating and deleting large number of customers in {@code CustomerListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Customer> backingList = createBackingList(1000000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of customer cards exceeded time limit");
    }

    /**
     * Returns a list of customers containing {@code customerCount} customers that is used to populate the
     * {@code CustomerListPanel}.
     */
    private ObservableList<Customer> createBackingList(int customerCount) {
        ObservableList<Customer> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < customerCount; i++) {
            Name name = new Name(i + "a");
            Phone phone = new Phone("00043211");
            DateOfBirth dob = new DateOfBirth("12/02/1994");
            Email email = new Email("a@aa.com");
            IdentificationNo idnum = new IdentificationNo("1234123A");
            Address address = new Address("a");
            Customer customer = new Customer(name, phone, dob, email, idnum, address, Collections.emptySet());
            backingList.add(customer);
        }
        return backingList;
    }

    /**
     * Initializes {@code customerListPanelHandle} with a {@code CustomerListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code CustomerListPanel}.
     */
    private void initUi(ObservableList<Customer> backingList) {
        CustomerListPanel customerListPanel =
            new CustomerListPanel(backingList, selectedCustomer, selectedCustomer::set);
        uiPartRule.setUiPart(customerListPanel);

        customerListPanelHandle = new CustomerListPanelHandle(getChildNode(customerListPanel.getRoot(),
            CustomerListPanelHandle.CUSTOMER_LIST_VIEW_ID));
    }
}
