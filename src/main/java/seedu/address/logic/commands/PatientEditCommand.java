package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.datetime.DateOfBirth;
import seedu.address.model.description.Description;
import seedu.address.model.nextofkin.NextOfKin;
import seedu.address.model.nextofkin.NextOfKinRelation;
import seedu.address.model.patient.DrugAllergy;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Sex;
import seedu.address.model.patient.exceptions.PersonIsNotPatient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

/**
 * Edits the details of an existing person in the address book.
 */
public class PatientEditCommand extends Command {

    public static final String COMMAND_WORD = "patientedit";
    public static final String COMMAND_WORD2 = "pedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " or " + COMMAND_WORD2
            + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_SEX + "SEX] "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_YEAR + "DOB] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public PatientEditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
        if (editPersonDescriptor.getName().isPresent() || editPersonDescriptor.getNric().isPresent()) {
            model.getAddressBook().getTaskList().stream()
                    .filter(z -> z.getLinkedPatient() != null && z.getLinkedPatient().getLinkedPatientNric()
                            .equals(((Patient) personToEdit).getNric().getNric()))
                    .forEach(task -> {
                        Task replacement = task.isCopy() ? new Task(task) : new Task(task, true);
                        replacement.setLinkedPatient(editedPerson.getName(), ((Patient) editedPerson).getNric());
                        model.setTask(task, replacement);
                    });
        }
        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.setPerson(personToEdit, editedPerson);

        if (personToEdit.isCopy()) {
            personToEdit.editCopy();
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson.getName()));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        if (personToEdit instanceof Patient) {
            Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
            Sex updatedSex = editPersonDescriptor.getSex().orElse(((Patient) personToEdit).getSex());
            Nric updatedNric = editPersonDescriptor.getNric().orElse(((Patient) personToEdit).getNric());
            DateOfBirth updatedDob = editPersonDescriptor.getDateOfBirth().orElse(((Patient) personToEdit)
                    .getDateOfBirth());
            Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
            Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
            Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
            DrugAllergy updatedDrugAllergy =
                editPersonDescriptor.getDrugAllergy().orElse(((Patient) personToEdit).getDrugAllergy());
            Description updatedDesc =
                editPersonDescriptor.getDescription().orElse(((Patient) personToEdit).getPatientDesc());

            //NextOfKin Attributes
            NextOfKin kin = ((Patient) personToEdit).getNextOfKin();

            Name updatedKinName = editPersonDescriptor.getNextOfKinName().orElse(kin.getName());
            NextOfKinRelation updatedKinRelation =
                editPersonDescriptor.getNextOfKinRelation().orElse(kin.getKinRelation());
            Phone updatedKinPhone = editPersonDescriptor.getNextOfKinPhone().orElse(kin.getPhone());
            Email updatedKinEmail = kin.getEmail();
            Address updatedKinAddress;
            if (editPersonDescriptor.getSameAddr()) {
                updatedKinAddress = updatedAddress;
            } else {
                updatedKinAddress = editPersonDescriptor.getNextOfKinAddress().orElse(kin.getAddress());
            }

            return new Patient(updatedName, updatedPhone, updatedEmail, updatedAddress, null, updatedNric,
                updatedDob, updatedSex, updatedDrugAllergy,
                new NextOfKin(updatedKinName, updatedKinPhone, updatedKinEmail, updatedKinAddress, null,
                    updatedKinRelation), updatedDesc);
        } else {
            throw new PersonIsNotPatient();
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PatientEditCommand)) {
            return false;
        }

        // state check
        PatientEditCommand e = (PatientEditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Sex sex;
        private Nric nric;
        private DateOfBirth dateOfBirth;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private DrugAllergy drugAllergy;
        private Description description;

        //For Next Of Kin
        private Name nextOfKinName;
        private NextOfKinRelation nextOfKinRelation;
        private Phone nextOfKinPhone;
        private Address nextOfKinAddress;
        private boolean isSameAddr = false;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setSex(toCopy.sex);
            setNric(toCopy.nric);
            setDateOfBirth(toCopy.dateOfBirth);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setNextOfKinName(toCopy.nextOfKinName);
            setNextOfKinRelation(toCopy.nextOfKinRelation);
            setNextOfKinPhone(toCopy.nextOfKinPhone);
            setNextOfKinAddress(toCopy.nextOfKinAddress);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, nric, dateOfBirth, phone, email, address, tags, sex, drugAllergy,
                nextOfKinName, nextOfKinPhone, nextOfKinRelation, nextOfKinAddress, description);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setSex(Sex sex) {
            this.sex = sex;
        }

        public Optional<Sex> getSex() {
            return Optional.ofNullable(sex);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setDateOfBirth(DateOfBirth dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public Optional<DateOfBirth> getDateOfBirth() {
            return Optional.ofNullable(dateOfBirth);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setDrugAllergy(DrugAllergy drugAllergy) {
            this.drugAllergy = drugAllergy;
        }

        public Optional<DrugAllergy> getDrugAllergy() {
            return Optional.ofNullable(drugAllergy);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        /* Setters for NextOfKin */
        public void setNextOfKinName(Name nextOfKinName) {
            this.nextOfKinName = nextOfKinName;
        }

        public Optional<Name> getNextOfKinName() {
            return Optional.ofNullable(nextOfKinName);
        }

        public void setNextOfKinRelation(NextOfKinRelation nextOfKinRel) {
            this.nextOfKinRelation = nextOfKinRel;
        }

        public Optional<NextOfKinRelation> getNextOfKinRelation() {
            return Optional.ofNullable(nextOfKinRelation);
        }

        public void setNextOfKinPhone(Phone nextOfKinPhone) {
            this.nextOfKinPhone = nextOfKinPhone;
        }

        public Optional<Phone> getNextOfKinPhone() {
            return Optional.ofNullable(nextOfKinPhone);
        }

        public void setNextOfKinAddress(Address nextOfKinAddress) {
            this.nextOfKinAddress = nextOfKinAddress;
        }

        public Optional<Address> getNextOfKinAddress() {
            return Optional.ofNullable(nextOfKinAddress);
        }

        public void setSameAddr() {
            isSameAddr = true;
        }

        public boolean getSameAddr() {
            return isSameAddr;
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress());
        }
    }
}
