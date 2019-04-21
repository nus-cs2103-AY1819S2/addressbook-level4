package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.Statistics;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.request.Request;
import seedu.address.model.request.RequestDate;
import seedu.address.model.request.RequestStatus;
import seedu.address.model.tag.Condition;

/**
 * Edits an order in the request book.
 *
 * @@author daviddl9
 */
public class EditRequestCommand extends EditCommand implements RequestCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_OPTION
        + ": Edits the details of the order identified "
        + "by the index number used in the displayed request book. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + EDIT_COMMAND_PARAMETERS
        + "Example: " + COMMAND_WORD + " " + COMMAND_OPTION + " "
        + EDIT_COMMAND_EXAMPLE;

    public static final String MESSAGE_EDIT_REQUEST_SUCCESS = "Edited Request: %1$s";

    public static final int MIN_REQUEST_DURATION = 2;

    private final EditRequestDescriptor editRequestDescriptor;

    /**
     * @param index of the request in the filtered request list to edit
     * @param editRequestDescriptor details to edit the request with
     */
    public EditRequestCommand(Index index, EditRequestDescriptor editRequestDescriptor) {
        super(index);
        requireNonNull(editRequestDescriptor);

        this.editRequestDescriptor = new EditRequestDescriptor(editRequestDescriptor);
    }

    /**
     * Creates and returns a {@code Request} with the details of {@code requestToEdit}
     * edited with {@code editRequestDescriptor}.
     */
    private static Request createEditedRequest(Request requestToEdit,
                                               EditRequestDescriptor editRequestDescriptor,
                                               Model model) throws CommandException {
        assert requestToEdit != null;
        Name updatedName = editRequestDescriptor.getName().orElse(requestToEdit.getName());
        Phone updatedPhone = editRequestDescriptor.getPhone().orElse(requestToEdit.getPhone());
        Address updatedAddress = editRequestDescriptor.getAddress().orElse(requestToEdit.getAddress());
        RequestDate updatedRequestDate = editRequestDescriptor.getDate().orElse(requestToEdit.getRequestDate());
        if (editRequestDescriptor.getDate().isPresent()) {
            Calendar calendar = Calendar.getInstance();

            TreeSet<Date> healthWorkerDates = new TreeSet<>();

            String updatedHealthWorker;
            if (editRequestDescriptor.getHealthWorker().isPresent()) {
                updatedHealthWorker = editRequestDescriptor.getHealthWorker().toString();
            } else {
                updatedHealthWorker = requestToEdit.getHealthStaff();
            }

            for (Request request : model.getFilteredRequestList()) {
                if (updatedHealthWorker != null && updatedHealthWorker.equals(request.getHealthStaff())) {
                    healthWorkerDates.add(request.getRequestDate().getDate());
                }
            }

            Date date = updatedRequestDate.getDate();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, -MIN_REQUEST_DURATION);
            Date lowerLimit = calendar.getTime();
            calendar.add(Calendar.HOUR_OF_DAY, 2 * MIN_REQUEST_DURATION);
            Date upperLimit = calendar.getTime();

            if (healthWorkerDates.contains(date) || (healthWorkerDates.lower(date) != null && healthWorkerDates
                .lower(date).after(lowerLimit))
                || (healthWorkerDates.higher(date) != null && healthWorkerDates.ceiling(date).before(upperLimit))) {
                throw new CommandException(Messages.MESSAGE_EDITED_TIME_HEALTHWORKER_UNAVAILABLE);
            }
        }
        Nric updatedNric = editRequestDescriptor.getNric().orElse(requestToEdit.getNric());
        RequestStatus updatedRequestStatus =
            editRequestDescriptor.getRequestStatus().orElse(requestToEdit.getRequestStatus());
        Set<Condition> updatedConditions = editRequestDescriptor.getConditions().orElse(requestToEdit
                .getConditions());
        String updatedHealthWorker;
        if (requestToEdit.getHealthStaff() != null && !updatedRequestStatus.equals(new RequestStatus("PENDING"))) {
            updatedHealthWorker = requestToEdit.getHealthStaff();
        } else {
            updatedHealthWorker = null;
        }

        if (updatedHealthWorker == null) {
            return new Request(updatedName, updatedNric, updatedPhone, updatedAddress,
                updatedRequestDate, updatedConditions, updatedRequestStatus);
        } else {
            return new Request(updatedName, updatedNric, updatedPhone, updatedAddress,
                updatedRequestDate, updatedConditions, updatedRequestStatus, updatedHealthWorker);
        }

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(model, history);
        List<Request> lastShownList = model.getFilteredRequestList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX);
        }

        Request requestToEdit = lastShownList.get(index.getZeroBased());
        Request editedRequest = createEditedRequest(requestToEdit, editRequestDescriptor, model);

        if (!requestToEdit.isSameRequest(editedRequest) && model.hasRequest(editedRequest)) {
            throw new CommandException(MESSAGE_DUPLICATE_REQUEST);
        }

        Statistics.deleteStatistics(requestToEdit.getConditions());
        Statistics.updateStatistics(editedRequest.getConditions());

        edit(model, requestToEdit, editedRequest);
        return new CommandResult(String.format(MESSAGE_EDIT_REQUEST_SUCCESS, editedRequest));
        // List<Request> lastShownList = model.getF()
    }

    @Override
    public void edit(Model model, Object toEdit, Object edited) {
        model.setRequest((Request) toEdit, (Request) edited);
        model.updateFilteredRequestList(Model.PREDICATE_SHOW_ALL_REQUESTS);
        commitRequestBook(model);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditRequestCommand)) {
            return false;
        }

        EditRequestCommand e = (EditRequestCommand) other;
        return index.equals(e.index) && editRequestDescriptor.equals(((EditRequestCommand) other)
            .editRequestDescriptor);
    }

    /**
     * Stores the details to edit the request with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    public static class EditRequestDescriptor {
        private Name name;
        private Phone phone;
        private Address address;
        private RequestDate requestDate;
        private Nric nric;
        private Set<Condition> conditions;
        private String healthWorker;
        private RequestStatus requestStatus;

        public EditRequestDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code request} is used internally.
         */
        public EditRequestDescriptor(EditRequestDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setAddress(toCopy.address);
            setDate(toCopy.requestDate);
            setNric(toCopy.nric);
            setConditions(toCopy.conditions);
            setHealthWorker(toCopy.healthWorker);
            setRequestStatus(toCopy.requestStatus);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, nric, phone, address, requestDate,
                conditions, requestStatus);
        }

        public Optional<RequestStatus> getRequestStatus() {
            return Optional.ofNullable(requestStatus);
        }

        public void setRequestStatus(RequestStatus requestStatus) {
            this.requestStatus = requestStatus;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<RequestDate> getDate() {
            return Optional.ofNullable(requestDate);
        }

        public void setDate(RequestDate requestDate) {
            this.requestDate = requestDate;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<String> getHealthWorker() {
            return Optional.ofNullable(healthWorker);
        }

        public void setHealthWorker(String healthWorker) {
            this.healthWorker = healthWorker;
        }

        /**
         * Returns an unmodifiable condition set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code conditions} is null
         */
        public Optional<Set<Condition>> getConditions() {
            return (conditions != null) ? Optional.of(Collections.unmodifiableSet(conditions))
                : Optional.empty();
        }

        /**
         * Sets {@code conditions} to this object's {@code conditions}
         * A defensive copy of {@code conditions} is used internally.
         */
        public void setConditions(Set<Condition> conditions) {
            this.conditions = (conditions != null) ? new HashSet<>(conditions) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRequestDescriptor)) {
                return false;
            }

            // state check
            EditRequestDescriptor editRequestDescriptor = (EditRequestDescriptor) other;

            return getName().equals(editRequestDescriptor.getName())
                && getPhone().equals(editRequestDescriptor.getPhone())
                && getNric().equals(editRequestDescriptor.getNric())
                && getAddress().equals(editRequestDescriptor.getAddress())
                && getDate().equals(editRequestDescriptor.getDate())
                && getConditions().equals(editRequestDescriptor.getConditions())
                && getHealthWorker().equals(editRequestDescriptor.getHealthWorker());
        }
    }
}
