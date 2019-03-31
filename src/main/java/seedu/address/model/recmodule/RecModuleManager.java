package seedu.address.model.recmodule;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.VersionedGradTrak;
import seedu.address.model.course.Course;
import seedu.address.model.moduleinfo.ModuleInfo;

/**
 * A class for managing the module recommendation feature.
 */
public class RecModuleManager {

    private final RecModulePredicate predicate;
    private final RecModuleComparator comparator;

    public RecModuleManager(Course course, VersionedGradTrak versionedGradTrak) {
        requireAllNonNull(course, versionedGradTrak);
        predicate = new RecModulePredicate(course, versionedGradTrak);
        comparator = new RecModuleComparator();
    }

    public RecModulePredicate getRecModulePredicate() {
        return predicate;
    }

    public RecModuleComparator getRecModuleComparator() {
        return comparator;
    }

    public static ObservableList<RecModule> getObservableRecModuleList(ObservableList<ModuleInfo> moduleInfoList) {
        ArrayList<RecModule> recModuleList = new ArrayList<>();
        for (ModuleInfo moduleInfo : moduleInfoList) {
            recModuleList.add(new RecModule(moduleInfo.getModuleInfoCode()));
        }

        return FXCollections.observableArrayList(recModuleList);
    }
}
