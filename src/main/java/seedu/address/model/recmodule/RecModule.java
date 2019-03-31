package seedu.address.model.recmodule;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * A class for storing {@code ModuleInfoCode} and {@code CourseReqType}.
 */
public class RecModule {

    private final ModuleInfoCode moduleInfoCode;
    private CourseReqType courseReqType;

    public RecModule(ModuleInfoCode code) {
        requireNonNull(code);
        this.moduleInfoCode = code;
    }

    public ModuleInfoCode getModuleInfoCode() {
        return moduleInfoCode;
    }

    public Optional<CourseReqType> getCourseReqType() {
        return Optional.ofNullable(courseReqType);
    }

    public void setCourseReqType(CourseReqType type) {
        courseReqType = type;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof RecModule)) {
            return false;
        }

        RecModule other = (RecModule) object;
        return moduleInfoCode.equals(other.moduleInfoCode)
                && getCourseReqType().equals(other.getCourseReqType());
    }
}
