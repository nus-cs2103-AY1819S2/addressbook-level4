package seedu.hms.model.customer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_DATE_OF_BIRTH_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.hms.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.hms.testutil.TypicalCustomers.ALICE;
import static seedu.hms.testutil.TypicalCustomers.BOB;
import static seedu.hms.testutil.TypicalCustomers.VIP_CUSTOMER;
import static seedu.hms.testutil.TypicalCustomers.VIP_CUSTOMER2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.hms.testutil.CustomerBuilder;

public class CustomerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Customer customer = new CustomerBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        customer.getTags().remove(0);
    }

    @Test
    public void isSameCustomer() {
        // same object -> returns true
        assertTrue(ALICE.isSameCustomer(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameCustomer(null));

        // different phone, email and id -> returns false
        Customer editedAlice =
            new CustomerBuilder(ALICE).withIdNum(VALID_ID_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameCustomer(editedAlice));


        // different name -> returns true
        editedAlice = new CustomerBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSameCustomer(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice =
            new CustomerBuilder(ALICE).withDateOfBirth(VALID_DATE_OF_BIRTH_BOB)
                .withEmail(VALID_EMAIL_BOB).withIdNum(VALID_ID_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        assertTrue(ALICE.isSameCustomer(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice =
            new CustomerBuilder(ALICE).withPhone(VALID_PHONE_BOB).withDateOfBirth(VALID_DATE_OF_BIRTH_BOB)
                .withIdNum(VALID_ID_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        assertTrue(ALICE.isSameCustomer(editedAlice));


        // same name, same id, different attributes -> returns true
        editedAlice =
            new CustomerBuilder(ALICE).withDateOfBirth(VALID_DATE_OF_BIRTH_BOB)
                .withEmail(VALID_EMAIL_BOB).withPhone(VALID_PHONE_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCustomer(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice =
            new CustomerBuilder(ALICE).withIdNum(VALID_ID_BOB).withAddress(VALID_ADDRESS_BOB)
                .withDateOfBirth(VALID_DATE_OF_BIRTH_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCustomer(editedAlice));

        // same name, same phone, same email, same id, different attributes -> returns true
        editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withDateOfBirth(VALID_DATE_OF_BIRTH_BOB)
            .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCustomer(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new CustomerBuilder(ALICE).withIdNum(VALID_ID_BOB).withDateOfBirth(VALID_DATE_OF_BIRTH_BOB)
            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCustomer(editedAlice));

        // same name, same phone, same date of birth different attributes -> returns true
        editedAlice = new CustomerBuilder(ALICE).withIdNum(VALID_ID_BOB).withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCustomer(editedAlice));

        // same name, same email, same date of birth different attributes -> returns true
        editedAlice = new CustomerBuilder(ALICE).withIdNum(VALID_ID_BOB).withPhone(VALID_PHONE_BOB)
            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCustomer(editedAlice));

        // same phone, same email, same date of birth different attributes -> returns true
        editedAlice = new CustomerBuilder(ALICE).withIdNum(VALID_ID_BOB).withName(VALID_NAME_BOB)
            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCustomer(editedAlice));
    }


    @Test
    public void equals() {
        // same values -> returns true
        Customer aliceCopy = new CustomerBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different customer -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Customer editedAlice = new CustomerBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new CustomerBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new CustomerBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different id -> returns false
        editedAlice = new CustomerBuilder(ALICE).withIdNum(VALID_ID_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different date of birth -> returns false
        editedAlice = new CustomerBuilder(ALICE).withDateOfBirth(VALID_DATE_OF_BIRTH_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new CustomerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new CustomerBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void isVip() {
        // VIP Customer -> returns true
        assertTrue(VIP_CUSTOMER.isVip());
        assertTrue(VIP_CUSTOMER2.isVip());

        // Normal Customer -> returns false
        assertFalse(ALICE.isVip());
    }
}
