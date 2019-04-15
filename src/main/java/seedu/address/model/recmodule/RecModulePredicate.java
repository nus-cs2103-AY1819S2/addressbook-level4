package seedu.address.model.recmodule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.EligibleModulePredicate;
import seedu.address.model.ReadOnlyGradTrak;
import seedu.address.model.course.Course;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Tests if a module can be recommended to the user.
 */
public class RecModulePredicate implements Predicate<RecModule> {

    private final Course course;
    private final ReadOnlyGradTrak gradTrak;
    private final Logger logger;

    public RecModulePredicate(Course course, ReadOnlyGradTrak gradTrak) {
        requireAllNonNull(course, gradTrak);
        this.course = course;
        this.gradTrak = gradTrak;
        this.logger = LogsCenter.getLogger(getClass());
    }

    @Override
    public boolean test(RecModule recModule) {
        /* ineligible module */
        if (!(new EligibleModulePredicate(gradTrak).test(recModule.getModuleInfo()))) {
            return false;
        }

        ModuleInfoCode codeToTest = recModule.getCode();
        List<ModuleInfoCode> nonFailedCodeList = gradTrak.getNonFailedCodeList();
        /* eligible module */
        List<CourseReqType> reqTypeList = course.getCourseReqTypeOf(codeToTest);
        for (CourseReqType reqType : reqTypeList) { // starting from most important requirement
            if (course.isCodeContributing(reqType, nonFailedCodeList, codeToTest)) {
                recModule.setCourseReqType(reqType);
                logger.fine(codeToTest.toString() + " fulfills " + reqType.name());
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecModulePredicate // instanceof handles nulls
                && course.equals(((RecModulePredicate) other).course)
                && gradTrak.equals(((RecModulePredicate) other).gradTrak)); // state check
    }
}
