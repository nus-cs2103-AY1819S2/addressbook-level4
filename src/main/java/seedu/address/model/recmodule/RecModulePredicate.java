package seedu.address.model.recmodule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.EligibleModulePredicate;
import seedu.address.model.VersionedGradTrak;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Tests if a module can be recommended to the user.
 */
public class RecModulePredicate implements Predicate<RecModule> {

    private final Course course;
    private final VersionedGradTrak versionedGradTrak;
    private final Logger logger;


    public RecModulePredicate(Course course, VersionedGradTrak versionedGradTrak) {
        requireAllNonNull(course, versionedGradTrak);
        this.course = course;
        this.versionedGradTrak = versionedGradTrak;
        this.logger = LogsCenter.getLogger(getClass());
    }

    @Override
    public boolean test(RecModule recModule) {
        ModuleInfoCode code = recModule.getModuleInfoCode();

        /* module cannot be taken */
        if (!(new EligibleModulePredicate().test(code))) {
            return false;
        }

        /* module can be taken */
        if (versionedGradTrak.hasUnfinishedModule(code)) {
            // module already in plan
            return false;
        }

        List<ModuleInfoCode> passedModuleList = versionedGradTrak.getPassedModuleList();
        List<CourseReqType> reqTypeList = course.getCourseReqTypeOf(code);
        for (CourseReqType reqType : reqTypeList) { // starting from most important requirement
            if (!course.isReqFulfilled(reqType, passedModuleList)) {
                // module can contribute towards unfulfilled requirement
                recModule.setCourseReqType(reqType);
                logger.fine(code.toString() + " fulfills " + reqType.name());
                return true;
            }
        }

        return false;
    }
}
