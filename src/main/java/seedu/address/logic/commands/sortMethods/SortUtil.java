package seedu.address.logic.commands.sortMethods;

import seedu.address.model.person.Person;
import seedu.address.model.tag.SkillsTag;

import java.util.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//SHOULD ANY OF THESE BE IN THE MODEL/PERSON/[] CLASS?
public class SortUtil {
    public static LinkedHashSet<SkillsTag> toTags(List<SkillsTag> tags) {
        // Needs a LinkedHashSet to withtain the order
        final LinkedHashSet <SkillsTag> tagSet = new LinkedHashSet<>();
        if (tags.isEmpty()) {
            return tagSet;
        }
        for (SkillsTag tagName : tags) {
            tagSet.add(tagName);
        }
        return tagSet;
    }

    public static List<SkillsTag> sortSkillTags(List<SkillsTag> skills) {
        //Sorting tags in alphabetical order
        List<SkillsTag> sortedlist = skills.stream().sorted(Comparator.comparing(SkillsTag::toString)).collect(Collectors.toList());
        return sortedlist;
    }

    public static List<Person> sortPersonsByTags(List<Person> persons) {
        //Sort persons by tags
        List<Person> sortedlist = persons.stream().sorted(Comparator.comparing(Person::tagsToString)).collect(Collectors.toList());
        return sortedlist;
    }

    public static List<Person> sortPersonsByNames(List<Person> persons) {
        //Sort persons by tags
        List<Person> sortedlist = persons.stream().sorted(Comparator.comparing(Person::namesToString)).collect(Collectors.toList());
        return sortedlist;
    }
}