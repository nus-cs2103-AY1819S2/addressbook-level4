package seedu.address.model.module;

import java.util.Objects;

public class Module {

    private final ModuleCode moduleCode;
    private final ModuleTitle moduleTitle;
    private final ModuleDescription moduleDescription;
    private final ModulePrereq modulePrereq;
    private final ModuleDepartment moduleDepartment;
    private final ModuleCredits moduleCredits;

    public Module(ModuleCode moduleCode, ModuleTitle moduleTitle,
                  ModuleDescription moduleDescription, ModulePrereq modulePrereq,
                  ModuleDepartment moduleDepartment, ModuleCredits moduleCredits) {
        this.moduleCode = moduleCode;
        this.moduleTitle = moduleTitle;
        this.moduleDescription = moduleDescription;
        this.modulePrereq = modulePrereq;
        this.moduleDepartment = moduleDepartment;
        this.moduleCredits = moduleCredits;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public ModuleTitle getModuleTitle() {
        return moduleTitle;
    }

    public ModuleDescription getModuleDescription() {
        return moduleDescription;
    }

    public ModulePrereq getModulePrereq() {
        return modulePrereq;
    }

    public ModuleDepartment getModuleDepartment() {
        return moduleDepartment;
    }

    public ModuleCredits getModuleCredits() {
        return moduleCredits;
    }

    public boolean isSameModule(Module other) {
        if (other == this) {
            return true;
        }

        return other != null
                && (other.getModuleCode().equals(getModuleCode())
                || other.getModuleTitle().equals(getModuleTitle()));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleCode, moduleTitle, moduleDescription, moduleDepartment
                , modulePrereq, moduleCredits);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getModuleTitle())
                .append(" Module Code: ")
                .append(getModuleCode())
                .append(" Module Description: ")
                .append(getModuleDescription())
                .append(" Department: ")
                .append(getModuleDepartment())
                .append(" Module Credits")
                .append(getModuleCredits());
        return builder.toString();
    }
}
