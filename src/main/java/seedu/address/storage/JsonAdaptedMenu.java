package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
//import seedu.address.model.person.Address;
//import seedu.address.model.person.Email;
//import seedu.address.model.person.Name;
//import seedu.address.model.person.Person;
//import seedu.address.model.person.Phone;
//import seedu.address.model.tag.Tag;
//import seedu.address.model.menuItem.MenuItem;
//import seedu.address.model.menuItem.Code;
//import seedu.address.model.menuItem.Name;
//import seedu.address.model.menuItem.Price;

/**
 * Jackson-friendly version of {@link MenuItem}.
 */
class JsonAdaptedMenu {

    /*public static final String MISSING_FIELD_MESSAGE_FORMAT = "Menu Item's %s field is missing!";

    private final String name;
    private final String code;
    private final String price;

    *//**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     *//*
    @JsonCreator
    public JsonAdaptedMenu(@JsonProperty("name") String name, @JsonProperty("code") String code,
                             @JsonProperty("price") String price) {
        this.name = name;
        this.code = code;
        this.price = price;
    }

    *//**
     * Converts a given {@code MenuItem} into this class for Jackson use.
     *//*
    public JsonAdaptedMenu(MenuItem item) {
        name = item.getName().fullName;
        code = item.getCode();
        price = item.getPrice().value;
    }

    *//**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     *//*
    public MenuItem toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (code == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Code.class.getSimpleName()));
        }
        if (!Code.isValidCode(code)) {
            throw new IllegalValueException(Code.MESSAGE_CONSTRAINTS);
        }
        final Code modelCode = new Code(code);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        return new MenuItem(modelName, modelCode, modelPrice);
    }*/

}
