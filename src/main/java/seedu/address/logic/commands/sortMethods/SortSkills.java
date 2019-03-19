package seedu.address.logic.commands.sortMethods;

import seedu.address.model.person.*;
import seedu.address.model.tag.SkillsTag;

import java.util.*;


/**
 * Sorts all persons by skill (tag).
 * Currently only the first (alphabetically) is ordered.
 */
public class SortSkills {

    private List<Person> newList;

    public List<Person> getList() {
        return this.newList;
    }

    private List<Person> OrderPersonsTags(List<Person> lastShownList) {
        List<Person> personsWithCorrectTagOrder = new ArrayList<>();
        for (int i=0; i<lastShownList.size(); i++) {
            //Change Set to List to utilise stream sorting
            List<SkillsTag> individualSkills= new ArrayList<>();
            individualSkills.addAll(lastShownList.get(i).getTags());
            List<SkillsTag> individualSortedSkills = SortUtil.sortSkillTags(individualSkills);

            Name name = lastShownList.get(i).getName();
            Phone phone = lastShownList.get(i).getPhone();
            Email email = lastShownList.get(i).getEmail();
            Address address = lastShownList.get(i).getAddress();
            //change list back to set
            LinkedHashSet<SkillsTag> tagSet = SortUtil.toTags(individualSortedSkills);

            Person person = new Person(name, phone, email, address, tagSet);
            personsWithCorrectTagOrder.add(person);
        }
        return personsWithCorrectTagOrder;
    }

    public SortSkills(List<Person> lastShownList) {
        //Modify each Person to organise tags alphabetically
        List<Person> personsWithCorrectTagOrder = OrderPersonsTags(lastShownList);
        //Sort Persons alphabetically by tags
        List<Person> newList = SortUtil.sortPersonsByTags(personsWithCorrectTagOrder);

        this.newList = newList;
    }
}
