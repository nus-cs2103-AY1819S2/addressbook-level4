package seedu.address.model;

import java.util.Comparator;

import seedu.address.model.moduleinfo.ModuleInfo;

public class RecModuleComparator implements Comparator<ModuleInfo> {

    @Override
    public int compare(ModuleInfo first, ModuleInfo second) {
        //TODO: implement priority according to req type

        return first.getCodeString().compareTo(second.getCodeString());
    }
}
