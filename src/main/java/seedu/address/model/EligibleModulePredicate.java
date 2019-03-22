package seedu.address.model;

import java.util.function.Predicate;

import seedu.address.model.moduleinfo.ModuleInfo;

public class EligibleModulePredicate implements Predicate<ModuleInfo> {

    @Override
    public boolean test(ModuleInfo module) {


        return true;
    }
}
