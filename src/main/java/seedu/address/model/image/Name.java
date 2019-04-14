/* @@author Carrein */
package seedu.address.model.image;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Image's name in FomoFoto's Album.
 * A Name comprises of three fields: A fully qualified name, the base name and the file extension.
 * Guarantees: immutable; is valid as declared in {@link #isValidExtension(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * Ensures the name of the file has to be valid and of an image type.
     */
    public static final String VALIDATION_REGEX = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp|tiff|tif))$)";

    public final String baseName;
    public final String extName;
    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param baseName A valid base name.
     * @param extName  A valid extension.
     * @param fullName A valid extension.
     */
    public Name(String baseName, String extName, String fullName) {
        requireNonNull(baseName);
        requireNonNull(extName);
        requireNonNull(fullName);
        checkArgument(isValidExtension(fullName), MESSAGE_CONSTRAINTS);
        this.baseName = baseName;
        this.extName = extName;
        this.fullName = fullName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidExtension(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getBaseName() {
        return baseName;
    }

    public String getExtName() {
        return extName;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return baseName + "." + extName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && fullName.equals(((Name) other).fullName)); // state check
    }
}
