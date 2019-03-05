package seedu.address.storage.coursestorage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.course.CourseReqCredits;
import seedu.address.model.course.CourseReqDesc;
import seedu.address.model.course.CourseReqName;
import seedu.address.model.course.CourseRequirement;
import seedu.address.model.module.Module;
import seedu.address.storage.moduleinfostorage.JsonAdaptedModuleInfo;

/**
 * Jackson-friendly version of {@link CourseRequirement}.
 */
public class JsonAdaptedCourseRequirement {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Course requirement's %s field is missing!";
    private final String courseReqName;
    private final String courseReqDesc;
    private final String courseReqCredits;
    private final List<JsonAdaptedModuleInfo> modules = new ArrayList<>();
    /**
     * Constructs a {@code JsonAdaptedCourseRequirement} with the given Course Requirement details.
     */
    @JsonCreator
    public JsonAdaptedCourseRequirement(@JsonProperty("courseReqName") String courseReqName,
                                        @JsonProperty("courseReqDesc") String courseReqDesc,
                                        @JsonProperty("courseReqCredits") String courseReqCredits,
                                        @JsonProperty("modules") List<JsonAdaptedModuleInfo> modules) {
        this.courseReqName = courseReqName;
        this.courseReqDesc = courseReqDesc;
        this.courseReqCredits = courseReqCredits;
        if (modules != null) {
            this.modules.addAll(modules);
        }
    }

    /**
     * Converts a given {@code CourseRequirement} into this class for Jackson use.
     */
    public JsonAdaptedCourseRequirement(CourseRequirement source) {
        courseReqName = source.getCourseReqName().toString();
        courseReqDesc = source.getCourseReqDesc().toString();
        courseReqCredits = source.getCourseReqCredits().toString();
        //TODO: Initialise new modules object based on JsonAdaptedModuleInfo / Module Info List
    }

    /* TODO: toModelType() once ModuleInfo and other things that has been completed
     *
     */
    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Course Requirement} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Course Requirement.
     */
    public CourseRequirement toModelType() throws IllegalValueException {

        if (courseReqName == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, CourseReqName.class.getSimpleName()));
        }
        if (!CourseReqName.isValidCourseReqName(courseReqName)) {
            throw new IllegalValueException(CourseReqName.MESSAGE_CONSTRAINTS);
        }
        final CourseReqName modelCourseReqName = new CourseReqName(courseReqName);

        if (courseReqDesc == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CourseReqDesc.class.getSimpleName()));
        }
        if (!CourseReqDesc.isValidCourseReqDesc(courseReqDesc)) {
            throw new IllegalValueException(CourseReqDesc.MESSAGE_CONSTRAINTS);
        }
        final CourseReqDesc modelCourseReqDesc = new CourseReqDesc(courseReqDesc);

        if (courseReqCredits == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CourseReqCredits.class.getSimpleName()));
        }

        if (!CourseReqCredits.isValidCourseReqCredits(Integer.valueOf(courseReqCredits))) {
            throw new IllegalValueException(CourseReqCredits.MESSAGE_CONSTRAINTS);
        }
        final CourseReqCredits modelCourseReqCredit = new CourseReqCredits(Integer.valueOf(courseReqCredits));

        /* TODO: Implement necessary changes to module info
         */
        final Module[] modules = new Module[0];

        return new CourseRequirement(modelCourseReqName, modelCourseReqDesc,
                modelCourseReqCredit, modules);
    }
}
