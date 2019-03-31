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

    /**
     * Generates a List of {@code RecModule} from a List of {@code ModuleInfo}
     * for initialisation in {@code ModelManager}.
     * @param moduleInfoList The List of {@code ModuleInfo}.
     * @return A List of {@code RecModule}.
     */
    public static ObservableList<RecModule> getObservableRecModuleList(ObservableList<ModuleInfo> moduleInfoList) {
        ArrayList<RecModule> recModuleList = new ArrayList<>();
        for (ModuleInfo moduleInfo : moduleInfoList) {
            recModuleList.add(new RecModule(moduleInfo.getModuleInfoCode(), moduleInfo.getModuleInfoTitle()));
        }

        return FXCollections.observableArrayList(recModuleList);
    }
}
