package seedu.address.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.pdf.*;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Pdf}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Pdf's %s field is missing!";

    private final String name;
    private final String size;
    private final String location;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    private final String phone;
    private final String email;
    private final String address;


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given pdf details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given pdf details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("location") String location,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        File newFile = new File(location);
        this.name = newFile.getName();
        this.size = Long.toString(newFile.getTotalSpace());
        this.location = location;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }

    }

    /**
     * Converts a given {@code Pdf} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Pdf source) {
        name = source.getName().fullName;
        size = source.getSize().value;
        location = source.getLocation().value.toString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }


    /**
     * Converts this Jackson-friendly adapted pdf object into the model's {@code Pdf} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted pdf.
     */
    public Pdf toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (size == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Size.class.getSimpleName()));
        }
        if (!Size.isValidSize(size)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        Size modelSize = new Size(size);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Pdf(modelName, modelLocation, modelSize, modelTags);
    }

}
