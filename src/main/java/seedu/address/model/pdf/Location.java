package seedu.address.model.pdf;

import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Location {
    public static final String MESSAGE_CONSTRAINTS = "Locations of file can take any values, " +
            "and it should not be blank";

    public final Path path;

    public Location(String location) throws IOException {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        path = Paths.get(location).toRealPath(LinkOption.NOFOLLOW_LINKS);
    }

    /**
     * Returns if a given string is a valid email.
     */
    public boolean isValidLocation(String test) {
        return path.getFileName().equals(Paths.get(test));
    }
}
