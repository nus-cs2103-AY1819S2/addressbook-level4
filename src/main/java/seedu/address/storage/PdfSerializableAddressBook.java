package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyAddressBook;

/**
 * An Immutable AddressBook that is serializable to PDF format.
 */
class PdfSerializableAddressBook {

    private final List<PdfAdaptedPerson> persons = new ArrayList<>();
    private final List<PdfAdaptedTask> tasks = new ArrayList<>();

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public PdfSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(PdfAdaptedPerson::new).collect(Collectors.toList()));
        tasks.addAll(source.getTaskList().stream().map(PdfAdaptedTask::new).collect(Collectors.toList()));
    }

    public List<PdfAdaptedPerson> getPersons() {
        return persons;
    }

    public List<PdfAdaptedTask> getTasks() {
        return tasks;
    }
}
