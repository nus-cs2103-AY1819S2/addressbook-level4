package seedu.address.model.recmodule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.EligibleModulePredicate;
import seedu.address.model.GradTrak;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Tests if a module can be recommended to the user.
 */
public class RecModulePredicate implements Predicate<RecModule> {

    private final Course course;
    private final GradTrak gradTrak;
    private final Logger logger;

    public RecModulePredicate(Course course, GradTrak gradTrak) {
        requireAllNonNull(course, gradTrak);
        this.course = course;
        this.gradTrak = gradTrak;
        this.logger = LogsCenter.getLogger(getClass());
    }

    @Override
    public boolean test(RecModule recModule) {
        ModuleInfoCode moduleInfoCode = recModule.getModuleInfoCode();

        /* ineligible module */
        if (!(new EligibleModulePredicate(gradTrak).test(moduleInfoCode))) {
            return false;
        }

        /* eligible module */
        List<ModuleInfoCode> nonFailedCodeList = gradTrak.getNonFailedCodeList();
        List<CourseReqType> reqTypeList = course.getCourseReqTypeOf(moduleInfoCode);
        for (CourseReqType reqType : reqTypeList) { // starting from most important requirement
            if (!course.isReqFulfilled(reqType, nonFailedCodeList)) {
                // module can contribute towards unfulfilled requirement
                recModule.setCourseReqType(reqType);
                logger.fine(moduleInfoCode.toString() + " fulfills " + reqType.name());
                return true;
            }
        }

        return false;
    }
}
