package seedu.address.model;

import java.util.function.Predicate;

import seedu.address.model.moduleinfo.ModuleInfoCode;

/**
 * Tests if a module can be taken by a user.
 */
public class EligibleModulePredicate implements Predicate<ModuleInfoCode> {

    @Override
    public boolean test(ModuleInfoCode moduleInfoCode) {

        return true;
    }
}
