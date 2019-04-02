package seedu.address.storage;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RENTALPRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLINGPRICE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
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
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String remark;
    private final String customer;
    private final String address;
    private final String sellingPrice;
    private final String rentalPrice;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("remark") String remark,
            @JsonProperty("address") String address, @JsonProperty("customer") String customer,
            @JsonProperty("sellingPrice") String sellingPrice, @JsonProperty("rentalPrice") String rentalPrice) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.remark = remark;
        this.customer = customer;
        this.address = address;
        this.sellingPrice = sellingPrice;
        this.rentalPrice = rentalPrice;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        remark = source.getRemark().value;
        if (source instanceof Buyer) {
            customer = "buyer";
            address = null;
            rentalPrice = null;
            sellingPrice = null;
        } else if (source instanceof Seller) {
            final Seller referenceSeller = (Seller) source;
            customer = "seller";
            address = referenceSeller.getAddress().value;
            sellingPrice = referenceSeller.getSellingPrice().toString();
            tagged.addAll(referenceSeller.getTags().stream()
                    .map(JsonAdaptedTag::new)
                    .collect(Collectors.toList()));
            rentalPrice = null;
        } else if (source instanceof Landlord) {
            final Landlord referenceLandlord = (Landlord) source;
            customer = "landlord";
            address = referenceLandlord.getAddress().value;
            rentalPrice = referenceLandlord.getRentalPrice().toString();
            tagged.addAll(referenceLandlord.getTags().stream()
                    .map(JsonAdaptedTag::new)
                    .collect(Collectors.toList()));
            sellingPrice = null;
        } else if (source instanceof Tenant) {
            customer = "tenant";
            address = null;
            rentalPrice = null;
            sellingPrice = null;
        } else {
            customer = null;
            address = null;
            rentalPrice = null;
            sellingPrice = null;
        }
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        if (!Remark.isValidRemark(remark)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Remark modelRemark = new Remark(remark);

        if (customer == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, String.class.getSimpleName()));
        }

        switch (customer) {
            case "buyer":
                return new Buyer(modelName, modelPhone, modelEmail, modelRemark);
            case "seller": {
                if (address == null) {
                    throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                            Address.class.getSimpleName()));
                }
                if (!Address.isValidAddress(address)) {
                    throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
                }
                final Address modelAddress = new Address(address);

                if (sellingPrice == null) {
                    throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                            Price.class.getSimpleName()));
                }
                if (!Price.isValidPrice(sellingPrice)) {
                    throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
                }
                final Price modelSellingPrice = new Price(sellingPrice);

                final List<Tag> personTags = new ArrayList<>();
                for (JsonAdaptedTag tag : tagged) {
                    personTags.add(tag.toModelType());
                }
                final Set<Tag> modelTags = new HashSet<>(personTags);
                return new Seller(modelName, modelPhone, modelEmail, modelRemark,
                        new Property(Property.PROPERTY_TYPE_SELL, modelAddress, modelSellingPrice, modelTags));
            }
            case "landlord": {
                if (address == null) {
                    throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                            Address.class.getSimpleName()));
                }
                if (!Address.isValidAddress(address)) {
                    throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
                }
                final Address modelAddress = new Address(address);

                if (rentalPrice == null) {
                    throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                            Price.class.getSimpleName()));
                }
                if (!Price.isValidPrice(rentalPrice)) {
                    throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
                }
                final Price modelRentalPrice = new Price(rentalPrice);

                final List<Tag> personTags = new ArrayList<>();
                for (JsonAdaptedTag tag : tagged) {
                    personTags.add(tag.toModelType());
                }
                final Set<Tag> modelTags = new HashSet<>(personTags);
                return new Landlord(modelName, modelPhone, modelEmail, modelRemark,
                        new Property(Property.PROPERTY_TYPE_RENT, modelAddress, modelRentalPrice, modelTags));
            }
            case "tenant":
                return new Tenant(modelName, modelPhone, modelEmail, modelRemark);
            default:
                return new Person(modelName, modelPhone, modelEmail, modelRemark);
        }
    }

}
