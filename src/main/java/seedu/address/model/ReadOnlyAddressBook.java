package seedu.address.model;

import java.util.ArrayList;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook extends Observable {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    // quickdocs method to access data of AddressBook
    ArrayList<Patient> getPatients();

}
