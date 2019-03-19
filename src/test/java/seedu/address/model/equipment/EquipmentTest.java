package seedu.address.model.equipment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalEquipments.ACHORVALECC;
import static seedu.address.testutil.TypicalEquipments.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.EquipmentBuilder;

public class EquipmentTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Equipment equipment = new EquipmentBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        equipment.getTags().remove(0);
    }

    @Test
    public void isSameEquipment() {
        // same object -> returns true
        assertTrue(ACHORVALECC.isSameEquipment(ACHORVALECC));

        // null -> returns false
        assertFalse(ACHORVALECC.isSameEquipment(null));

        // same name and other attributes except serial number -> return False
        Equipment editedAlice = new EquipmentBuilder(ACHORVALECC).withSerialNumber(VALID_SERIAL_NUMBER_BOB).build();
        assertFalse(ACHORVALECC.isSameEquipment(editedAlice));

        // same serial number, same name, different attributes -> return true
        editedAlice = new EquipmentBuilder(ACHORVALECC).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ACHORVALECC.isSameEquipment(editedAlice));

        // same serial number, same email, different attributes -> return true
        editedAlice = new EquipmentBuilder(ACHORVALECC).withName(VALID_NAME_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ACHORVALECC.isSameEquipment(editedAlice));

        // same serial number, same address, different attributes -> return true
        editedAlice = new EquipmentBuilder(ACHORVALECC).withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ACHORVALECC.isSameEquipment(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Equipment aliceCopy = new EquipmentBuilder(ACHORVALECC).build();
        assertTrue(ACHORVALECC.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ACHORVALECC.equals(ACHORVALECC));

        // null -> returns false
        assertFalse(ACHORVALECC.equals(null));

        // different type -> returns false
        assertFalse(ACHORVALECC.equals(5));

        // different equipment -> returns false
        assertFalse(ACHORVALECC.equals(BOB));

        // different name -> returns false
        Equipment editedAlice = new EquipmentBuilder(ACHORVALECC).withName(VALID_NAME_BOB).build();
        assertFalse(ACHORVALECC.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new EquipmentBuilder(ACHORVALECC).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ACHORVALECC.equals(editedAlice));

        // different email -> returns false
        editedAlice = new EquipmentBuilder(ACHORVALECC).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ACHORVALECC.equals(editedAlice));

        // different address -> returns false
        editedAlice = new EquipmentBuilder(ACHORVALECC).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ACHORVALECC.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new EquipmentBuilder(ACHORVALECC).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ACHORVALECC.equals(editedAlice));
    }
}
