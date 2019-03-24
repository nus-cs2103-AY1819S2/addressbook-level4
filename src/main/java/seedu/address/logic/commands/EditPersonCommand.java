//package seedu.address.logic.commands;
//
//import static java.util.Objects.requireNonNull;
//import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
//
//import java.util.List;
//import java.util.Optional;
//
//import seedu.address.commons.core.Messages;
//import seedu.address.commons.core.index.Index;
//import seedu.address.commons.util.CollectionUtil;
//import seedu.address.logic.CommandHistory;
//import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.model.Model;
//import seedu.address.model.person.Name;
//import seedu.address.model.person.Nric;
//import seedu.address.model.person.Person;
//import seedu.address.model.person.Phone;
//
///**
// * Edits the details of an existing person in the address book.
// */
//public class EditPersonCommand extends EditCommand implements PersonCommand {
//
//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
//            + "by the index number used in the displayed person list. "
//            + "Existing values will be overwritten by the input values.\n"
//            + "Parameters: INDEX (must be a positive integer) "
//            + EDIT_COMMAND_PARAMETERS + "\n"
//            + "Example: " + COMMAND_WORD + " 1 "
//            + EDIT_COMMAND_EXAMPLE;
//
//    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
//
//    private final EditPersonDescriptor editPersonDescriptor;
//
//    /**
//     * @param index of the person in the filtered person list to edit
//     * @param editPersonDescriptor details to edit the person with
//     */
//    public EditPersonCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
//        super(index);
//        requireNonNull(editPersonDescriptor);
//
//        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
//    }
//
//    @Override
//    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
//        requireNonNull(model);
//        List<Person> lastShownList = model.getFilteredPersonList();
//
//        if (index.getZeroBased() >= lastShownList.size()) {
//            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//        }
//
//        Person personToEdit = lastShownList.get(index.getZeroBased());
//        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
//
//        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
//            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
//        }
//
//        edit(model, personToEdit, editedPerson);
//        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
//    }
//
//    @Override
//    public void edit(Model model, Object toEdit, Object edited) {
//        model.setPerson((Person) toEdit, (Person) edited);
//        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
//        model.commitAddressBook();
//    }
//
//    /**
//     * Creates and returns a {@code Person} with the details of {@code personToEdit}
//     * edited with {@code editPersonDescriptor}.
//     */
//    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
//        assert personToEdit != null;
//
//        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
//        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
//        Nric updatedNric = editPersonDescriptor.getNric().orElse(personToEdit.getNric());
//
//        return new Person(updatedName, updatedNric, updatedPhone);
//    }
//
//}
