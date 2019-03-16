package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVIEWSCORES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_JOBSAPPLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KNOWNPROGLANG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASTJOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.JobsApply;
import seedu.address.model.person.KnownProgLang;
import seedu.address.model.person.PastJob;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;


/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    public static String getAddAlias(Person person) {
        return AddCommand.COMMAND_ALIAS + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + person.getName().fullName + " ");
        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
        sb.append(PREFIX_NRIC + person.getNric().value + " ");
        sb.append(PREFIX_GENDER + person.getGender().value + " ");
        sb.append(PREFIX_RACE + person.getRace().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        sb.append(PREFIX_SCHOOL + person.getSchool().value + " ");
        sb.append(PREFIX_MAJOR + person.getMajor().value + " ");
        sb.append(PREFIX_GRADE + person.getGrade().value + " ");
        person.getKnownProgLangs().stream().forEach(
            s -> sb.append(PREFIX_KNOWNPROGLANG + s.value + " ")
        );
        person.getPastJobs().stream().forEach(
            s -> sb.append(PREFIX_PASTJOB + s.value + " ")
        );
        person.getJobsApply().stream().forEach(
            s -> sb.append(PREFIX_JOBSAPPLY + s.value + " ")
        );
        sb.append(PREFIX_INTERVIEWSCORES + person.getInterviewScores().value + " ");
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getNric().ifPresent(nric -> sb.append(PREFIX_NRIC).append(nric.value).append(" "));
        descriptor.getGender().ifPresent(gender -> sb.append(PREFIX_GENDER).append(gender.value).append(" "));
        descriptor.getRace().ifPresent(race -> sb.append(PREFIX_RACE).append(race.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getSchool().ifPresent(school -> sb.append(PREFIX_SCHOOL).append(school.value).append(" "));
        descriptor.getMajor().ifPresent(major -> sb.append(PREFIX_MAJOR).append(major.value).append(" "));
        descriptor.getGrade().ifPresent(grade -> sb.append(PREFIX_GRADE).append(grade.value).append(" "));
        if (descriptor.getKnownProgLangs().isPresent()) {
            Set<KnownProgLang> knownProgLangs = descriptor.getKnownProgLangs().get();
            if (knownProgLangs.isEmpty()) {
                sb.append(PREFIX_KNOWNPROGLANG).append(" ");
            } else {
                knownProgLangs.forEach(s -> sb.append(PREFIX_KNOWNPROGLANG).append(s.value).append(" "));
            }
        }
        if (descriptor.getPastJobs().isPresent()) {
            Set<PastJob> pastjobs = descriptor.getPastJobs().get();
            if (pastjobs.isEmpty()) {
                sb.append(PREFIX_PASTJOB).append(" ");
            } else {
                pastjobs.forEach(s -> sb.append(PREFIX_PASTJOB).append(s.value).append(" "));
            }
        }
        if (descriptor.getJobsApply().isPresent()) {
            Set<JobsApply> jobsApply = descriptor.getJobsApply().get();
            if (jobsApply.isEmpty()) {
                sb.append(PREFIX_JOBSAPPLY).append(" ");
            } else {
                jobsApply.forEach(s -> sb.append(PREFIX_JOBSAPPLY).append(s.value).append(" "));
            }
        }
        descriptor.getInterviewScores().ifPresent(interviewScores -> sb.append(PREFIX_INTERVIEWSCORES)
                .append(interviewScores.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG).append(" ");
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
