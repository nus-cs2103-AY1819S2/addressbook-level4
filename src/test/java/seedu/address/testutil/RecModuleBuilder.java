package seedu.address.testutil;

import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduleinfo.ModuleInfoTitle;
import seedu.address.model.recmodule.RecModule;

/**
 * A utility class to help with building RecModule objects.
 */
public class RecModuleBuilder {

    private RecModule recModule;

    public RecModuleBuilder(ModuleInfoCode code, ModuleInfoTitle title) {
        recModule = new RecModule(code, title);
    }

    /**
     * Sets the given {@code courseReqType} of {@code RecModule}.
     * @param type The {@code courseReqType}.
     * @return This {@code RecModuleBuilder}.
     */
    public RecModuleBuilder withType(CourseReqType type) {
        recModule.setCourseReqType(type);
        return this;
    }

    public RecModule build() {
        return recModule;
    }
}
