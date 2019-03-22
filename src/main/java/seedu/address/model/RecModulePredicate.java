package seedu.address.model;

import java.util.function.Predicate;

import seedu.address.model.moduleinfo.ModuleInfo;

public class RecModulePredicate implements Predicate<ModuleInfo> {
    
    @Override
    public boolean test(ModuleInfo module) {
        //return (new EligibleModulePredicate().test(module)
        return true;
    }
}
