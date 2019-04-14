package seedu.address.model;

import java.util.function.Predicate;

import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduleinfo.ModuleInfoPrerequisites;

/**
 * Tests if a module can be read.
 */
public class EligibleModulePredicate implements Predicate<ModuleInfo> {

    /* The following accounts for some modules with 'A' level prerequisites */
    private static final String aLevelRegex =
            "MA1521|MA1101R|PC1141|PC1142|PC1143|PC1144|CM1401|CM1402|CM1501|CM1502|PC1431|";

    private final GradTrak gradTrak;

    public EligibleModulePredicate(GradTrak gradTrak) {
        this.gradTrak = gradTrak;
    }

    @Override
    public boolean test(ModuleInfo moduleInfo) {
        ModuleInfoCode code = moduleInfo.getModuleInfoCode();
        ModuleInfoPrerequisites prerequisites = moduleInfo.getModuleInfoPrerequisite();

        boolean isAdded = gradTrak.getNonFailedCodeList().contains(code);
        boolean isPrereqSatisfied = gradTrak.getMissingPrerequisites(prerequisites.getModuleTree()).isEmpty();

        if (code.toString().matches(aLevelRegex)) {
            isPrereqSatisfied = true;
        }

        return !isAdded && isPrereqSatisfied;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EligibleModulePredicate // instanceof handles nulls
                && gradTrak.equals(((EligibleModulePredicate) other).gradTrak));
    }
}
