package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.course.CourseRequirement;
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
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
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
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedCourseRequirement(CourseRequirement source) {
        courseReqName = source.getCourseReqName().toString();
        courseReqDesc = source.getCourseReqDesc().toString();
        courseReqCredits = source.getCourseReqCredits().toString();
        modules.addAll(source.getModules().stream()
                .map(JsonAdaptedModuleInfo::new)
                .collect(Collectors.toList()));
    }

    /* TODO: toModelType() once ModuleInfo and other things that has been completed
     *
     */
}
