package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * Creates a RecModule with the given {@code String} of {@code ModuleInfoCode} and {@code CourseReqType}.
     * @param code The {@code String} of {@code ModuleInfoCode}.
     * @param type The {@code CourseReqType}.
     * @return a RecModule with {@code ModuleInfo} represented by {@code code}
     * and {@code CourseReqType} of {@code type}.
     */
    public RecModule create(String code, CourseReqType type) {
        ModuleInfo moduleInfo = moduleInfoList.getModule(code);
        assert(moduleInfo != null);

        return new RecModule(moduleInfo, type);
    }

    /**
     * Creates a RecModule with the given {@code String} of {@code ModuleInfoCode}.
     * @param code The {@code String} of {@code ModuleInfoCode}.
     * @return a RecModule with {@code ModuleInfo} represented by {@code code}.
     */
    public RecModule create(String code) {
        ModuleInfo moduleInfo = moduleInfoList.getModule(code);
        assert(moduleInfo != null);

        return new RecModule(moduleInfo);
    }

    /**
     * Generates a list of {@code RecModule} representing all modules in {@code ModuleInfoList}.
     * @return a list of {@code RecModule} representing all modules in {@code ModuleInfoList}.
     */
    public List<RecModule> getAllModules() {
        List<RecModule> list = new ArrayList<>();
        for (ModuleInfo moduleInfo : moduleInfoList.getObservableList()) {
            list.add(new RecModule(moduleInfo));
        }

        return list;
    }
}
