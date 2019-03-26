package seedu.address.logic.commands.sortmethods;

import seedu.address.model.person.*;
import seedu.address.model.person.Gpa;
import seedu.address.model.tag.SkillsTag;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


/**
 * Sorts all persons by skill (tag).
 * Currently only the first (alphabetically) is ordered.
 */
public class SortSkills {

    private List<Person> newList;

    public List<Person> getList() {
        return this.newList;
    }

    public SortSkills(List<Person> lastShownList) {
        //Modify each Person to organise tags alphabetically
        List<Person> personsWithCorrectTagOrder = orderPersonsTags(lastShownList);
        //Sort Persons alphabetically by tags
        List<Person> newList = SortUtil.sortPersonsByTags(personsWithCorrectTagOrder);

        this.newList = newList;
    }

    /**
     * Gets from the list the information of person and processes
     */
    private List<Person> orderPersonsTags(List<Person> lastShownList) {
        List<Person> personsWithCorrectTagOrder = new ArrayList<>();
        for (int i = 0; i < lastShownList.size(); i++) {
            //Change Set to List to utilise stream sorting
            List<SkillsTag> individualSkills = new ArrayList<>();
            individualSkills.addAll(lastShownList.get(i).getTags());
            List<SkillsTag> individualSortedSkills = SortUtil.sortSkillTags(individualSkills);

            Name name = lastShownList.get(i).getName();
            Phone phone = lastShownList.get(i).getPhone();
            Email email = lastShownList.get(i).getEmail();
            Education education = lastShownList.get(i).getEducation();
            Gpa gpa = lastShownList.get(i).getGPA();
            Address address = lastShownList.get(i).getAddress();
            //change list back to set
            LinkedHashSet<SkillsTag> tagSet = SortUtil.toTags(individualSortedSkills);

            Person person = new Person(name, phone, email, education, gpa, address, tagSet);
            personsWithCorrectTagOrder.add(person);
        }
        return personsWithCorrectTagOrder;
    }
}
