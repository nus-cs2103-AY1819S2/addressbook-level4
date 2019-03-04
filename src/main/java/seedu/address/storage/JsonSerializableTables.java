package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTables;
import seedu.address.model.table.Tables;
import seedu.address.model.table.Table;

/**
 * An Immutable Tables that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableTables {

    public static final String MESSAGE_DUPLICATE_PERSON = "Table list contains duplicate table(s).";
    private final List<JsonAdaptedTable> tableList = new ArrayList<>();
    /**
     * Constructs a {@code JsonSerializableRestOrRant} with the given persons.
     */
    @JsonCreator
    public JsonSerializableTables(@JsonProperty("tableList") List<JsonAdaptedTable> tableList) {
        this.tableList.addAll(tableList);
    }

    /**
     * Converts a given {@code ReadOnlyRestOrRant} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRestOrRant}.
     */
    public JsonSerializableTables(ReadOnlyTables source) {
        tableList.addAll(source.getTableList().stream().map(JsonAdaptedTable::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code RestOrRant} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Tables toModelType() throws IllegalValueException {
        Tables tables = new Tables();
        for (JsonAdaptedTable jsonAdaptedTable : this.tableList) {
            Table table = jsonAdaptedTable.toModelType();
            if (tables.hasTable(table)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            tables.addTable(table);
        }
        return tables;
    }

}
