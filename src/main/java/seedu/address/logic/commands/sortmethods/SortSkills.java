package seedu.address.logic.commands.sortmethods;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

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

    public SortSkills(List<Person> lastShownList, String type) {
        String prefix = type.substring(0,1);
        //Modify each Person to organise tags alphabetically
        List<Person> personsWithCorrectTagOrder = orderPersonsTags(lastShownList, prefix);
        //Sort Persons alphabetically by tags
        List<Person> newList = SortUtil.sortPersonsByTags(personsWithCorrectTagOrder);

        this.newList = newList;
    }

    /**
     * Gets from the list the information of person and processes
     */
    private List<Person> orderPersonsTags(List<Person> lastShownList, String prefix) {
        List<Person> personsWithCorrectTagOrder = new ArrayList<>();
        for (int i = 0; i < lastShownList.size(); i++) {
            //Change Set to List to utilise stream sorting
            List<SkillsTag> individualTags = new ArrayList<>();
            List<SkillsTag> tagsToSort = new ArrayList<>();
            List<SkillsTag> otherTags = new ArrayList<>();
            individualTags.addAll(lastShownList.get(i).getTags());
            for (SkillsTag tag : individualTags) {
                String tagString = tag.toString();
                //first element of string is "["
                if (tagString.substring(1, 2).equals(prefix)) {
                    tagsToSort.add(tag);
                } else {
                    otherTags.add(tag);
                }
            }
            List<SkillsTag> individualSortedTags = SortUtil.sortSkillTags(tagsToSort);
            individualSortedTags.addAll(otherTags);

            Name name = lastShownList.get(i).getName();
            Phone phone = lastShownList.get(i).getPhone();
            Email email = lastShownList.get(i).getEmail();
            Education education = lastShownList.get(i).getEducation();
            Gpa gpa = lastShownList.get(i).getGpa();
            Address address = lastShownList.get(i).getAddress();
            //change list back to set
            LinkedHashSet<SkillsTag> tagSet = SortUtil.toTags(individualSortedTags);

            Person person = new Person(name, phone, email, education, gpa, address, tagSet);
            personsWithCorrectTagOrder.add(person);
        }
        return personsWithCorrectTagOrder;
    }

    public List<Person> getList() {
        return this.newList;
    }
}
