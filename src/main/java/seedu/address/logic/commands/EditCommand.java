package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_IDENTITY_FIELD;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_IDENTITY_FIELD_ARCHIVE;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_IDENTITY_FIELD_PIN;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON_ARCHIVE;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON_PIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTALPRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLINGPRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
import seedu.address.model.person.Address;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Landlord;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Seller;
import seedu.address.model.person.Tenant;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyType;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE_BUYER = "Parameters for buyer: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] " + "[" + PREFIX_PHONE + "PHONE] " + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_REMARK + "REMARK] \n"
            + "Example for buyer: " + COMMAND_WORD + " 1 " + PREFIX_PHONE + "82536172 " + PREFIX_EMAIL
            + "tommyh@example.com " + PREFIX_REMARK + "updated phone and email\n";

    public static final String MESSAGE_USAGE_SELLER = "Parameters for seller: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] " + "[" + PREFIX_PHONE + "PHONE] " + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_REMARK + "REMARK] " + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_SELLINGPRICE + "SELLING_PRICE] " + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example for seller: " + COMMAND_WORD + " 1 " + PREFIX_PHONE + "82515472 " + PREFIX_EMAIL
            + "stanleyking@example.com " + PREFIX_ADDRESS + "Blk 11 Marsiling Dr, #11-04 "
            + PREFIX_REMARK + "updated phone, email and address for property\n";

    public static final String MESSAGE_USAGE_LANDLORD = "Parameters for landlord: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] " + "[" + PREFIX_PHONE + "PHONE] " + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_REMARK + "REMARK] " + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_RENTALPRICE + "RENTAL_PRICE] " + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example for landlord: " + COMMAND_WORD + " 1 " + PREFIX_PHONE + "97628592 " + PREFIX_EMAIL
            + "kellyang@example.com " + PREFIX_ADDRESS + "112 Jalan Bukit Merah #07-02 "
            + PREFIX_REMARK + "updated phone, email and address for property\n";

    public static final String MESSAGE_USAGE_TENANT = "Parameters for tenant: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] " + "[" + PREFIX_PHONE + "PHONE] " + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_REMARK + "REMARK] \n"
            + "Example for tenant: " + COMMAND_WORD + " 1 " + PREFIX_PHONE + "97649382 " + PREFIX_EMAIL
            + "bobkoh@example.com " + PREFIX_REMARK + "updated phone and email\n";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + MESSAGE_USAGE_BUYER + MESSAGE_USAGE_SELLER + MESSAGE_USAGE_LANDLORD + MESSAGE_USAGE_TENANT;

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    private static final String MESSAGE_ILLEGAL_EDIT_COMMAND = "One or more of the parameters are not applicable for "
            + "the selected customer.\n";


    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
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

        if (!isLegalEdit(personToEdit, editPersonDescriptor)) {
            if (personToEdit instanceof Buyer) {
                throw new CommandException(MESSAGE_ILLEGAL_EDIT_COMMAND + MESSAGE_USAGE_BUYER);
            }
            if (personToEdit instanceof Seller) {
                throw new CommandException(MESSAGE_ILLEGAL_EDIT_COMMAND + MESSAGE_USAGE_SELLER);
            }
            if (personToEdit instanceof Landlord) {
                throw new CommandException(MESSAGE_ILLEGAL_EDIT_COMMAND + MESSAGE_USAGE_LANDLORD);
            }
            if (personToEdit instanceof Tenant) {
                throw new CommandException(MESSAGE_ILLEGAL_EDIT_COMMAND + MESSAGE_USAGE_TENANT);
            }
        }
        if (!personToEdit.equals(editedPerson)) {
            if (model.hasPerson(editedPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } else if (model.hasPersonArchive(editedPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON_ARCHIVE);
            } else if (model.hasPersonPin(editedPerson)) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON_PIN);
            }
        }

        if (model.hasEditedPerson(personToEdit, editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_IDENTITY_FIELD);
        } else if (model.hasEditedPersonArchive(personToEdit, editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_IDENTITY_FIELD_ARCHIVE);
        } else if (model.hasEditedPersonPin(personToEdit, editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_IDENTITY_FIELD_PIN);
        }

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitBooks();
        model.setSelectedPinPerson(null);
        model.setSelectedPerson(editedPerson);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    @Override
    public boolean requiresMainList() {
        return true;
    }

    @Override
    public boolean requiresArchiveList() {
        return false;
    }

    /**
     * Returns true if only the correct information is being edited for each customer type
     */
    private static Boolean isLegalEdit(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        if (((personToEdit instanceof Buyer || personToEdit instanceof Tenant)
                && (editPersonDescriptor.getAddress().isPresent() || editPersonDescriptor.getSellingPrice().isPresent()
                || editPersonDescriptor.getRentalPrice().isPresent() || editPersonDescriptor.getTags().isPresent()))
                || (personToEdit instanceof Seller && editPersonDescriptor.getRentalPrice().isPresent())
                || (personToEdit instanceof Landlord && editPersonDescriptor.getSellingPrice().isPresent())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Remark updatedRemark = editPersonDescriptor.getRemark().orElse(personToEdit.getRemark());

        if (personToEdit instanceof Buyer) {
            return new Buyer(updatedName, updatedPhone, updatedEmail, updatedRemark);
        }

        if (personToEdit instanceof Seller) {
            final Seller referenceSeller = (Seller) personToEdit;
            Address updatedAddress = editPersonDescriptor.getAddress().orElse(referenceSeller.getAddress());
            Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(referenceSeller.getTags());
            Price updatedSellingPrice = editPersonDescriptor.getSellingPrice()
                    .orElse(referenceSeller.getSellingPrice());
            return new Seller(updatedName, updatedPhone, updatedEmail, updatedRemark,
                    new Property(PropertyType.SELLING, updatedAddress, updatedSellingPrice, updatedTags));
        }

        if (personToEdit instanceof Landlord) {
            final Landlord referenceLandlord = (Landlord) personToEdit;
            Address updatedAddress = editPersonDescriptor.getAddress().orElse(referenceLandlord.getAddress());
            Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(referenceLandlord.getTags());
            Price updatedRentalPrice = editPersonDescriptor.getRentalPrice().orElse(referenceLandlord.getRentalPrice());
            return new Landlord(updatedName, updatedPhone, updatedEmail, updatedRemark,
                    new Property(PropertyType.RENTAL, updatedAddress, updatedRentalPrice, updatedTags));
        }

        if (personToEdit instanceof Tenant) {
            return new Tenant(updatedName, updatedPhone, updatedEmail, updatedRemark);
        }
        return new Person(updatedName, updatedPhone, updatedEmail, updatedRemark);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Price sellingPrice;
        private Price rentalPrice;
        private Remark remark;

        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setSellingPrice(toCopy.sellingPrice);
            setRentalPrice(toCopy.rentalPrice);
            setRemark(toCopy.remark);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags, sellingPrice, rentalPrice, remark);
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

        public void setSellingPrice(Price price) {
            this.sellingPrice = price;
        }

        public Optional<Price> getSellingPrice() {
            return Optional.ofNullable(sellingPrice);
        }

        public void setRentalPrice(Price price) {
            this.rentalPrice = price;
        }

        public Optional<Price> getRentalPrice() {
            return Optional.ofNullable(rentalPrice);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
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
                    && getRemark().equals(e.getRemark())
                    && getAddress().equals(e.getAddress())
                    && getRentalPrice().equals(e.getRentalPrice())
                    && getSellingPrice().equals(e.getSellingPrice())
                    && getTags().equals(e.getTags());
        }
    }
}
