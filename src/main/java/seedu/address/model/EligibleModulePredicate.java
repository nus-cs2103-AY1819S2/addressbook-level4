package seedu.address.model;

import java.util.function.Predicate;

import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduleinfo.ModuleInfoPrerequisites;

/**
 * Tests if a module can be read.
 */
public class EligibleModulePredicate implements Predicate<ModuleInfo> {

    private final GradTrak gradTrak;

    public EligibleModulePredicate(GradTrak gradTrak) {
        this.gradTrak = gradTrak;
    }

    @Override
    public boolean test(ModuleInfo moduleInfo) {
        ModuleInfoCode code = moduleInfo.getModuleInfoCode();
        ModuleInfoPrerequisites prerequisites = moduleInfo.getModuleInfoPrerequisite();

        return !gradTrak.getNonFailedCodeList().contains(code)
                && gradTrak.getMissingPrerequisites(prerequisites.getModuleTree()).isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EligibleModulePredicate // instanceof handles nulls
                && gradTrak.equals(((EligibleModulePredicate) other).gradTrak));
    }
}
