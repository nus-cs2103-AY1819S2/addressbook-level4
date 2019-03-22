package seedu.address.storage;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.pdf.Deadline;
import seedu.address.model.pdf.Directory;
import seedu.address.model.pdf.Name;
import seedu.address.model.pdf.Pdf;
import seedu.address.model.pdf.Phone;
import seedu.address.model.pdf.Size;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Pdf}.
 */
class JsonAdaptedPdf {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Pdf's %s field is missing!";

    private final String name;
    private final String size;
    private final String directory;
    private final String deadline;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

//    /**
//     * Constructs a {@code JsonAdaptedPdf} with the given pdf details.
//     */
//    @JsonCreator
//    public JsonAdaptedPdf(@JsonProperty("name") String name, @JsonProperty("directory") String directory,
//                          @JsonProperty("size") String size, @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
//        this.name = name;
//        this.directory = directory;
//        this.size = size;
//        this.deadline = null;
//        if (tagged != null) {
//            this.tagged.addAll(tagged);
//        }
//    }

    /**
     * Constructs a {@code JsonAdaptedPdf} with the given pdf details.
     */
    @JsonCreator
    public JsonAdaptedPdf(@JsonProperty("name") String name, @JsonProperty("directory") String directory,
                          @JsonProperty("size") String size, @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                          @JsonProperty("deadline") String deadline) {
        this.name = name;
        this.directory = directory;
        this.size = size;
        this.deadline = deadline;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Pdf} into this class for Jackson use.
     */
    public JsonAdaptedPdf(Pdf source) {
        name = source.getName().getFullName();
        size = source.getSize().getValue();
        directory = source.getDirectory().getDirectory();
        deadline = source.getDeadline().toString();
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

        final Set<Tag> modelTags = new HashSet<>(personTags);

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

        if (directory == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Directory.class.getSimpleName()));
        }
        if (!Directory.isValidDirectory(directory)) {
            throw new IllegalValueException(Directory.MESSAGE_CONSTRAINTS);
        }
        final Directory modelDirectory = new Directory(directory);

        /*if (deadline == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName()));
        }*/

        try {
            final Deadline modelDeadline = new Deadline(deadline);
            return new Pdf(modelName, modelDirectory, modelSize, modelTags, modelDeadline);
        } catch (DateTimeException e) {
            throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
        }

    }

}
