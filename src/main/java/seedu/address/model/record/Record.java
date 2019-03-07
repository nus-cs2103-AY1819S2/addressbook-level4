package seedu.address.model.record;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.datetime.Date;
import seedu.address.model.datetime.Time;
import seedu.address.model.description.Description;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;


/**
 * Represents a Record
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Record {

    // Identity field
    private final Person person;

    // Data field
    private final Procedure procedure;
    private final Date date;
    private final Time time;
    private final Name doctorName;
    private final Description description;

    public Record(Person person, Procedure procedure, Date date, Time time, Name docName, Description desc) {
        requireAllNonNull(person, procedure, date, time, docName, desc);
        this.person = person;
        this.procedure = procedure;
        this.date = date;
        this.time = time;
        this.doctorName = docName;
        this.description = desc;
    }

    public Person getPerson() {
        return person;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Name getDoctorName() {
        return doctorName;
    }

    public Description getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof Record)) {
            return false;
        }

        Record otherRecord = (Record) other;

        return otherRecord.getPerson().equals(getPerson())
                && otherRecord.getProcedure().equals(getProcedure())
                && otherRecord.getDate().equals(getDate())
                && otherRecord.getTime().equals(getTime())
                && otherRecord.getDoctorName().equals(getDoctorName())
                && otherRecord.getDescription().equals(getDescription());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append(" Procedure: ")
                .append(getProcedure())
                .append(" Date: ")
                .append(getDate())
                .append(" Time: ")
                .append(getTime())
                .append(" Doctor: ")
                .append(getDoctorName())
                .append(" Description: ")
                .append(getDescription());

        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, procedure, date, time, doctorName, description);
    }
}
