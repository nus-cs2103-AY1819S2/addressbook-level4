package seedu.address.model.recmodule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Optional;

import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * A class for storing a {@code ModuleInfo} and its corresponding {@code CourseReqType}.
 */
public class RecModule {

    private final ModuleInfo moduleInfo;
    private CourseReqType courseReqType;

    public RecModule(ModuleInfo moduleInfo) {
        requireNonNull(moduleInfo);
        this.moduleInfo = moduleInfo;
    }

    public RecModule(ModuleInfo moduleInfo, CourseReqType courseReqType) {
        requireAllNonNull(moduleInfo, courseReqType);
        this.moduleInfo = moduleInfo;
        this.courseReqType = courseReqType;
    }

    public ModuleInfoCode getCode() {
        return moduleInfo.getModuleInfoCode();
    }

    public ModuleInfo getModuleInfo() {
        return moduleInfo;
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
        return moduleInfo.equals(other.moduleInfo)
                && getCourseReqType().equals(other.getCourseReqType());
    }
}
