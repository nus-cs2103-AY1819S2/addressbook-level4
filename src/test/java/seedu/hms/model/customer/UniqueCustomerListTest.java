package seedu.hms.model.customer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.hms.testutil.TypicalCustomers.ALICE;
import static seedu.hms.testutil.TypicalCustomers.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.hms.model.customer.exceptions.CustomerNotFoundException;
import seedu.hms.model.customer.exceptions.DuplicateCustomerException;
import seedu.hms.testutil.CustomerBuilder;

public class UniqueCustomerListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final UniqueCustomerList uniqueCustomerList = new UniqueCustomerList();

    @Test
    public void containsNullCustomerThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCustomerList.contains(null);
    }

    @Test
    public void containsCustomerNotInListReturnsFalse() {
        assertFalse(uniqueCustomerList.contains(ALICE));
    }

    @Test
    public void containsCustomerInListReturnsTrue() {
        uniqueCustomerList.add(ALICE);

        assertTrue(uniqueCustomerList.contains(ALICE));
    }

    @Test
    public void containsCustomerWithSameIdentityFieldsInListReturnsTrue() {
        uniqueCustomerList.add(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
            .build();
        assertTrue(uniqueCustomerList.contains(editedAlice));
    }

    @Test
    public void addNullCustomerThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCustomerList.add(null);
    }

    @Test
    public void addDuplicateCustomerThrowsDuplicateCustomerException() {
        uniqueCustomerList.add(ALICE);
        thrown.expect(DuplicateCustomerException.class);
        uniqueCustomerList.add(ALICE);
    }

    @Test
    public void setCustomerNullTargetCustomerThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCustomerList.setCustomer(null, ALICE);
    }

    @Test
    public void setCustomerNullEditedCustomerThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCustomerList.setCustomer(ALICE, null);
    }

    @Test
    public void setCustomerTargetCustomerNotInListThrowsCustomerNotFoundException() {
        thrown.expect(CustomerNotFoundException.class);
        uniqueCustomerList.setCustomer(ALICE, ALICE);
    }

    @Test
    public void setCustomerEditedCustomerIsSameCustomerSuccess() {
        uniqueCustomerList.add(ALICE);
        uniqueCustomerList.setCustomer(ALICE, ALICE);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(ALICE);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomerEditedCustomerHasSameIdentitySuccess() {
        uniqueCustomerList.add(ALICE);
        Customer editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
            .build();
        uniqueCustomerList.setCustomer(ALICE, editedAlice);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(editedAlice);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomerEditedCustomerHasDifferentIdentitySuccess() {
        uniqueCustomerList.add(ALICE);
        uniqueCustomerList.setCustomer(ALICE, BOB);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(BOB);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomerEditedCustomerHasNonUniqueIdentityThrowsDuplicateCustomerException() {
        uniqueCustomerList.add(ALICE);
        uniqueCustomerList.add(BOB);
        thrown.expect(DuplicateCustomerException.class);
        uniqueCustomerList.setCustomer(ALICE, BOB);
    }

    @Test
    public void removeNullCustomerThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCustomerList.remove(null);
    }

    @Test
    public void removeCustomerDoesNotExistThrowsCustomerNotFoundException() {
        thrown.expect(CustomerNotFoundException.class);
        uniqueCustomerList.remove(ALICE);
    }

    @Test
    public void removeExistingCustomerRemovesCustomer() {
        uniqueCustomerList.add(ALICE);
        uniqueCustomerList.remove(ALICE);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomersNullUniqueCustomerListThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCustomerList.setCustomers((UniqueCustomerList) null);
    }

    @Test
    public void setCustomersUniqueCustomerListReplacesOwnListWithProvidedUniqueCustomerList() {
        uniqueCustomerList.add(ALICE);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(BOB);
        uniqueCustomerList.setCustomers(expectedUniqueCustomerList);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomersNullListThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCustomerList.setCustomers((List<Customer>) null);
    }

    @Test
    public void setCustomersListReplacesOwnListWithProvidedList() {
        uniqueCustomerList.add(ALICE);
        List<Customer> customerList = Collections.singletonList(BOB);
        uniqueCustomerList.setCustomers(customerList);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(BOB);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomersListWithDuplicateCustomersThrowsDuplicateCustomerException() {
        List<Customer> listWithDuplicateCustomers = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateCustomerException.class);
        uniqueCustomerList.setCustomers(listWithDuplicateCustomers);
    }

    @Test
    public void asUnmodifiableObservableListModifyListThrowsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueCustomerList.asUnmodifiableObservableList().remove(0);
    }
}
