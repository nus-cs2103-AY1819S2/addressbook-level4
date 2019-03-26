package seedu.address.logic.commands.sortmethods;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;

import seedu.address.model.person.Address;
import seedu.address.model.person.Education;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gpa;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.SkillsTag;

/**
 * Sorts all persons by skill (tag).
 * Currently only the first (alphabetically) is ordered.
 */
public class SortSkills {

    private List<Person> newList;

    public SortSkills(List<Person> lastShownList) {
        //Modify each Person to organise tags alphabetically
        List<Person> personsWithCorrectTagOrder = orderPersonsTags(lastShownList);
        //Sort Persons alphabetically by tags
        List<Person> newList = SortUtil.sortPersonsByTags(personsWithCorrectTagOrder);

        this.newList = newList;
    }

    public List<Person> getList() {
        return this.newList;
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
            Gpa gpa = lastShownList.get(i).getGpa();
            Address address = lastShownList.get(i).getAddress();
            //change list back to set
            LinkedHashSet<SkillsTag> tagSet = SortUtil.toTags(individualSortedSkills);

            Person person = new Person(name, phone, email, education, gpa, address, tagSet);
            personsWithCorrectTagOrder.add(person);
        }
        return personsWithCorrectTagOrder;
    }
}
