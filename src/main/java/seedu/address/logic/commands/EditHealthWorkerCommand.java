package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CommandType;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.person.healthworker.Organization;
import seedu.address.model.tag.Skills;

/**
 * @author Lookaz
 * Edits the details of an existing HealthWorker object in the addressbook.
 */
public class EditHealthWorkerCommand extends EditCommand implements HealthWorkerCommand {

    public static final String MESSAGE_EDIT_HEALTHWORKER_SUCCESS = "Edited Health Worker: %1$s";

    public static final String MESSAGE_USAGE = EditCommand.COMMAND_WORD + " " + COMMAND_OPTION + ": "
            + "Edits the details health worker at the specified index number used in the displayed HealthWorker list "
            + "Parameters: INDEX (must be a positive integer) " + EDIT_COMMAND_PARAMETERS
            + "Example: " + EditCommand.COMMAND_WORD + ": " + EDIT_COMMAND_EXAMPLE;

    private final EditHealthWorkerDescriptor editHealthWorkerDescriptor;

    public EditHealthWorkerCommand(Index index, EditHealthWorkerDescriptor editHealthWorkerDescriptor) {
        super(index);
        requireNonNull(editHealthWorkerDescriptor);

        this.editHealthWorkerDescriptor = new EditHealthWorkerDescriptor(editHealthWorkerDescriptor);
    }

    @Override
    public void edit(Model model, Object toEdit, Object edited) {
        model.setHealthWorker((HealthWorker) toEdit, (HealthWorker) edited);
        model.updateFilteredHealthWorkerList(Model.PREDICATE_SHOW_ALL_HEALTHWORKERS);
        commitHealthWorkerBook(model);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<HealthWorker> lastShownList = model.getFilteredHealthWorkerList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        HealthWorker toEdit = lastShownList.get(index.getZeroBased());
        HealthWorker edited = createEditedHealthWorker(toEdit, model, editHealthWorkerDescriptor);

        if (!toEdit.isSameHealthWorker(edited) && model.hasHealthWorker(edited)) {
            throw new CommandException(DUPLICATE_HEALTH_WORKER);
        }

        edit(model, toEdit, edited);
        return new CommandResult(String.format(MESSAGE_EDIT_HEALTHWORKER_SUCCESS, edited));
    }

    /**
     * Creates and returns a {@code HealthWorker} with the details of {@code toEdit}
     * edited with {@code editHealthWorkerDescriptor}.
     */
    private static HealthWorker createEditedHealthWorker(HealthWorker toEdit, Model model,
                                                         EditHealthWorkerDescriptor editHealthWorkerDescriptor) {
        assert toEdit != null;

        Name updatedName = editHealthWorkerDescriptor.getName().orElse(toEdit.getName());
        Nric updatedNric = editHealthWorkerDescriptor.getNric().orElse(toEdit.getNric());
        if (editHealthWorkerDescriptor.getNric().isPresent()) {
            editAssignedRequestHealthWorker(model, updatedNric.toString(), toEdit.getNric().toString());
        }
        Phone updatedPhone = editHealthWorkerDescriptor.getPhone().orElse(toEdit.getPhone());
        Organization updatedOrganization = editHealthWorkerDescriptor.getOrganization()
                .orElse(toEdit.getOrganization());
        Skills updatedSkills = editHealthWorkerDescriptor.getSkills().orElse(toEdit.getSkills());

        return new HealthWorker(updatedName, updatedNric, updatedPhone, updatedOrganization, updatedSkills);
    }

    /**
     * Method that replaces all existence of old HealthWorker field in Request to the new HealthWorker upon editing
     * the Name field of the HealthWorker.
     */
    private static void editAssignedRequestHealthWorker(Model model, String newNric, String oldNric) {
        model.updateRequestOnNameEdit(oldNric, newNric);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditHealthWorkerCommand)) {
            return false;
        }

        EditHealthWorkerCommand e = (EditHealthWorkerCommand) other;
        return index.equals(e.index) && editHealthWorkerDescriptor.equals(e.editHealthWorkerDescriptor);
    }

    /**
     * Stores the details to edit the HealthWorker with. Each non-empty field value will replace the
     * corresponding field value of the HealthWorker.
     */
    public static class EditHealthWorkerDescriptor {

        protected Name name;
        protected Nric nric;
        protected Phone phone;
        private Organization organization;
        private Skills skills;



        public EditHealthWorkerDescriptor() {}

        public EditHealthWorkerDescriptor(EditHealthWorkerDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setNric(toCopy.nric);
            this.organization = toCopy.organization;
            this.skills = toCopy.skills;
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, nric, phone, organization, skills);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setOrganization(Organization organization) {
            this.organization = organization;
        }

        public Optional<Organization> getOrganization() {
            return Optional.ofNullable(this.organization);
        }

        public void setSkills(Skills skills) {
            this.skills = skills;
        }

        public Optional<Skills> getSkills() {
            return Optional.ofNullable(this.skills);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditHealthWorkerDescriptor)) {
                return false;
            }

            EditHealthWorkerDescriptor e = (EditHealthWorkerDescriptor) other;

            return getName().equals(e.getName())
                    && getNric().equals(e.getNric())
                    && getPhone().equals(e.getPhone())
                    && getOrganization().equals(e.getOrganization())
                    && getSkills().equals(e.getSkills());
        }
    }

    @Override
    public void commitHealthWorkerBook(Model model) {
        model.commit(CommandType.HEALTHWORKER_AND_REQUEST_COMMAND);
    }
}
