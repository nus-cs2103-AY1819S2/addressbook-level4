package seedu.address.model.recmodule;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduleinfo.ModuleInfoTitle;

/**
 * A class for storing a {@code ModuleInfoCode} and its corresponding {@code ModuleInfoTitle}, {@code CourseReqType}.
 */
public class RecModule {

    private final ModuleInfoCode moduleInfoCode;
    private final ModuleInfoTitle moduleInfoTitle;
    private CourseReqType courseReqType;

    public RecModule(ModuleInfoCode code, ModuleInfoTitle title) {
        requireNonNull(code);
        this.moduleInfoCode = code;
        this.moduleInfoTitle = title;
    }

    public ModuleInfoCode getModuleInfoCode() {
        return moduleInfoCode;
    }

    public Optional<ModuleInfoTitle> getModuleInfoTitle() {
        return Optional.ofNullable(moduleInfoTitle);
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
