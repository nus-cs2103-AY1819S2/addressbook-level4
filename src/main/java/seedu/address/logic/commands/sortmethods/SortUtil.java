package seedu.address.logic.commands.sortmethods;

import seedu.address.model.person.Person;
import seedu.address.model.tag.SkillsTag;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

//SHOULD ANY OF THESE BE IN THE MODEL/PERSON/[] CLASS?

/**
 * One of the sorting helper commands
 */
public class SortUtil {

    /**
     * Separates the tags
     */
    public static LinkedHashSet<SkillsTag> toTags(List<SkillsTag> tags) {
        // Needs a LinkedHashSet to maintain the order
        final LinkedHashSet<SkillsTag> tagSet = new LinkedHashSet<>();
        if (tags.isEmpty()) {
            return tagSet;
        }
        for (SkillsTag tagName : tags) {
            tagSet.add(tagName);
        }
        return tagSet;
    }

    /**
     * sorts the skills tags
     */
    public static List<SkillsTag> sortSkillTags(List<SkillsTag> skills) {
        //Sorting tags in alphabetical order
        List<SkillsTag> sortedlist =
                skills.stream().sorted(Comparator.comparing(SkillsTag::toString)).collect(Collectors.toList());
        return sortedlist;
    }

    /**
     * sorts the persons by their tags
     */
    public static List<Person> sortPersonsByTags(List<Person> persons) {
        //Sort persons by tags
        List<Person> sortedlist =
                persons.stream().sorted(Comparator.comparing(Person::tagsToString)).collect(Collectors.toList());
        return sortedlist;
    }

    /**
     * sorts the persons by their name
     */
    public static List<Person> sortPersonsByNames(List<Person> persons) {
        //Sort persons by tags
        List<Person> sortedlist =
                persons.stream().sorted(Comparator.comparing(Person::namesToString)).collect(Collectors.toList());
        return sortedlist;
    }
}
