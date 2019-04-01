package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduletaken.ModuleTaken;

/**
 * Tests if a module can be taken by a user.
 */
public class EligibleModulePredicate implements Predicate<ModuleInfoCode> {

    private final GradTrak gradTrak;

    public EligibleModulePredicate(GradTrak gradTrak) {
        this.gradTrak = gradTrak;
    }

    @Override
    public boolean test(ModuleInfoCode moduleInfoCode) {
        if (gradTrak.getNonFailedCodeList().contains(moduleInfoCode)) {
            return false;
        }

        //TODO: check prerequisites and preclusions
        return true;
    }
}
