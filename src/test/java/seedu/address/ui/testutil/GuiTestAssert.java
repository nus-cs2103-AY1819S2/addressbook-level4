package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.PersonCardHandle;
import guitests.guihandles.PersonListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Landlord;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;
import seedu.address.model.person.Tenant;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
        assertEquals(expectedCard.getRemark(), actualCard.getRemark());
        assertEquals(expectedCard.getCustomer(), actualCard.getCustomer());
        assertEquals(expectedCard.getRentalPrice(), actualCard.getRentalPrice());
        assertEquals(expectedCard.getSellingPrice(), actualCard.getSellingPrice());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getRemark().value, actualCard.getRemark());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedBuyer}.
     */
    public static void assertCardDisplaysPerson(Buyer expectedBuyer, PersonCardHandle actualCard) {
        assertEquals(expectedBuyer.getName().fullName, actualCard.getName());
        assertEquals(expectedBuyer.getPhone().value, actualCard.getPhone());
        assertEquals(expectedBuyer.getEmail().value, actualCard.getEmail());
        assertEquals(expectedBuyer.getRemark().value, actualCard.getRemark());
        assertEquals(Buyer.CUSTOMER_TYPE_BUYER, actualCard.getCustomer());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedTenant}.
     */
    public static void assertCardDisplaysPerson(Tenant expectedTenant, PersonCardHandle actualCard) {
        assertEquals(expectedTenant.getName().fullName, actualCard.getName());
        assertEquals(expectedTenant.getPhone().value, actualCard.getPhone());
        assertEquals(expectedTenant.getEmail().value, actualCard.getEmail());
        assertEquals(expectedTenant.getRemark().value, actualCard.getRemark());
        assertEquals(Tenant.CUSTOMER_TYPE_TENANT, actualCard.getCustomer());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedSeller}.
     */
    public static void assertCardDisplaysPerson(Seller expectedSeller, PersonCardHandle actualCard) {
        assertEquals(expectedSeller.getName().fullName, actualCard.getName());
        assertEquals(expectedSeller.getPhone().value, actualCard.getPhone());
        assertEquals(expectedSeller.getEmail().value, actualCard.getEmail());
        assertEquals(expectedSeller.getRemark().value, actualCard.getRemark());
        assertEquals(expectedSeller.getAddress().value, actualCard.getAddress());
        assertEquals(expectedSeller.getSellingPrice().value, actualCard.getSellingPrice());
        assertEquals(expectedSeller.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
        assertEquals(Seller.CUSTOMER_TYPE_SELLER, actualCard.getCustomer());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedLandlord}.
     */
    public static void assertCardDisplaysPerson(Landlord expectedLandlord, PersonCardHandle actualCard) {
        assertEquals(expectedLandlord.getName().fullName, actualCard.getName());
        assertEquals(expectedLandlord.getPhone().value, actualCard.getPhone());
        assertEquals(expectedLandlord.getEmail().value, actualCard.getEmail());
        assertEquals(expectedLandlord.getRemark().value, actualCard.getRemark());
        assertEquals(expectedLandlord.getAddress().value, actualCard.getAddress());
        assertEquals(expectedLandlord.getRentalPrice().value, actualCard.getRentalPrice());
        assertEquals(expectedLandlord.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
        assertEquals(Landlord.CUSTOMER_TYPE_LANDLORD, actualCard.getCustomer());
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
