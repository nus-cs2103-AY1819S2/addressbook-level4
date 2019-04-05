package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddDoctorCommand.MESSAGE_DUPLICATE_DOCTOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPECIALISATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DOCTORS;

import java.util.Collections;
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
import seedu.address.model.person.Doctor;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Year;
import seedu.address.model.tag.Specialisation;

/**
 * Edits the details of an existing doctor in the docX.
 */
public class EditDoctorCommand extends Command {

    public static final String COMMAND_WORD = "edit-doctor";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the doctor identified "
            + "by the index number used in the displayed doctor list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_YEAR + "YEAR] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_SPECIALISATION + "SPECIALISATION]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 ";

    public static final String MESSAGE_EDIT_DOCTOR_SUCCESS = "Edited Doctor: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PATIENT = "This doctor already exists in the docX.";

    private final Index index;
    private final EditDoctorDescriptor editDoctorDescriptor;

    /**
     * @param index                 of the doctor in the filtered doctor list to edit
     * @param editDoctorDescriptor details to edit the doctor with
     */
    public EditDoctorCommand(Index index, EditDoctorDescriptor editDoctorDescriptor) {
        requireNonNull(index);
        requireNonNull(editDoctorDescriptor);

        this.index = index;
        this.editDoctorDescriptor = new EditDoctorDescriptor(editDoctorDescriptor);
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Doctor> lastShownList = model.getFilteredDoctorList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DOCTOR_DISPLAYED_INDEX);
        }

        Doctor doctorToEdit = lastShownList.get(index.getZeroBased());
        Doctor editedDoctor = createEditedDoctor(doctorToEdit, editDoctorDescriptor);

        if (!doctorToEdit.isSameDoctor(editedDoctor) && model.hasDoctor(editedDoctor)) {
            throw new CommandException(MESSAGE_DUPLICATE_DOCTOR);
        }

        model.setDoctor(doctorToEdit, editedDoctor);
        model.updateFilteredDoctorList(PREDICATE_SHOW_ALL_DOCTORS);
        model.commitDocX();
        return new CommandResult(String.format(MESSAGE_EDIT_DOCTOR_SUCCESS, editedDoctor));
    }

    /**
     * Creates and returns a {@code Doctor} with the details of {@code doctorToEdit}
     * edited with {@code editDoctorDescriptor}.
     */
    private static Doctor createEditedDoctor(Doctor doctorToEdit, EditDoctorDescriptor editDoctorDescriptor) {
        assert doctorToEdit != null;

        Name updatedName = editDoctorDescriptor.getName().orElse(doctorToEdit.getName());
        Gender updatedGender = editDoctorDescriptor.getGender().orElse(doctorToEdit.getGender());
        Year updatedYear = editDoctorDescriptor.getYear().orElse(doctorToEdit.getYear());
        Phone updatedPhone = editDoctorDescriptor.getPhone().orElse(doctorToEdit.getPhone());
        Set<Specialisation> updatedSpecs = editDoctorDescriptor.getSpecs().orElse(doctorToEdit.getSpecs());

        return new Doctor(updatedName, updatedPhone, updatedGender, updatedYear, updatedSpecs);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditDoctorCommand)) {
            return false;
        }

        // state check
        EditDoctorCommand e = (EditDoctorCommand) other;
        return index.equals(e.index)
                && editDoctorDescriptor.equals(e.editDoctorDescriptor);
    }

    /**
     * Stores the details to edit the doctor with. Each non-empty field value will replace the
     * corresponding field value of the doctor.
     */
    public static class EditDoctorDescriptor {
        private Name name;
        private Gender gender;
        private Year year;
        private Phone phone;

        private Set<Specialisation> specs;

        public EditDoctorDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code specialisations} is used internally.
         */
        public EditDoctorDescriptor(EditDoctorDescriptor toCopy) {
            setName(toCopy.name);
            setGender(toCopy.gender);
            setYear(toCopy.year);
            setPhone(toCopy.phone);
            setSpecs(toCopy.specs);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, gender, year, phone, specs);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setGender(Gender gender) {
            this.gender = gender;
        }

        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        public void setYear(Year year) {
            this.year = year;
        }

        public Optional<Year> getYear() {
            return Optional.ofNullable(year);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        /**
         * Sets {@code specs} to this object's {@code specs}.
         * A defensive copy of {@code specs} is used internally.
         */
        public void setSpecs(Set<Specialisation> specs) {
            this.specs = (specs != null) ? new HashSet<>(specs) : null;
        }

        /**
         * Returns an unmodifiable specialisation set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code specs} is null.
         */
        public Optional<Set<Specialisation>> getSpecs() {
            return (specs != null) ? Optional.of(Collections.unmodifiableSet(specs)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditDoctorDescriptor)) {
                return false;
            }

            // state check
            EditDoctorDescriptor e = (EditDoctorDescriptor) other;

            return getName().equals(e.getName())
                    && getGender().equals(e.getGender())
                    && getYear().equals(e.getYear())
                    && getPhone().equals(e.getPhone())
                    && getSpecs().equals(e.getSpecs());
        }
    }
}
