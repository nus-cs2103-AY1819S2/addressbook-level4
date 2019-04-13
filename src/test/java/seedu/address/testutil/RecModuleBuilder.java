package seedu.address.testutil;

import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.course.CourseReqType;
import seedu.address.model.moduleinfo.ModuleInfo;
import seedu.address.model.moduleinfo.ModuleInfoList;
import seedu.address.model.recmodule.RecModule;
import seedu.address.storage.moduleinfostorage.ModuleInfoManager;

/**
 * A utility class to help with building RecModule objects.
 */
public class RecModuleBuilder {

    private ModuleInfoList moduleInfoList;

    public RecModuleBuilder() {
        ModuleInfoManager moduleInfoManager = new ModuleInfoManager();
        Optional<ModuleInfoList> list;
        try {
             list = moduleInfoManager.readModuleInfoFile();
        } catch (DataConversionException dce) {
            System.err.println("Error reading json");
            return;
        }
        assert (list.isPresent());
        moduleInfoList = list.get();
    }

    public RecModule create(String code, CourseReqType type) {
        ModuleInfo moduleInfo = moduleInfoList.getModule(code);
        assert(moduleInfo != null);

        return new RecModule(moduleInfo, type);
    }

    public RecModule create(String code) {
        ModuleInfo moduleInfo = moduleInfoList.getModule(code);
        assert(moduleInfo != null);

        return new RecModule(moduleInfo);
    }
}
