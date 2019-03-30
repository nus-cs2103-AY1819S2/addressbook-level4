package seedu.address.storage;

import java.math.BigDecimal;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.record.Statistics;

/**
 * Jackson-friendly version of {@Link Statistics}.
 */
public class JsonAdaptedStatistics {

    private int consultations;
    private BigDecimal revenue;
    private BigDecimal expenditure;
    private HashMap<String, Integer> medicines;
    private HashMap<String, Integer> symptoms;

    /**
     * Constructs a {@code JsonAdaptedStatistics} with the given Statistics.
     */
    @JsonCreator
    public JsonAdaptedStatistics(@JsonProperty("consultations") int consultations,
                                 @JsonProperty("revenue") BigDecimal revenue,
                                 @JsonProperty("expenditure") BigDecimal expenditure,
                                 @JsonProperty("medicines") HashMap<String, Integer> medicines,
                                 @JsonProperty("symptoms") HashMap<String, Integer> symptoms) {
        this.consultations = consultations;
        this.revenue = revenue;
        this.expenditure = expenditure;
        this.medicines = medicines;
        this.symptoms = symptoms;
    }

    /**
     * Converts a given {@code Statistics} into this class for Jackson use.
     */
    public JsonAdaptedStatistics(Statistics source) {
        this.consultations = source.getNoOfConsultations();
        this.revenue = source.getRevenue();
        this.expenditure = source.getExpenditure();
        this.medicines = source.getMedicinesCount();
        this.symptoms = source.getSymptomsCount();
    }

    /**
     * Converts this Jackson-friendly adapted Statistics object into the model's {@code Statistics} object.
     *
     * @throws IllegalArgumentException if there were any data constraints violated for Statistics fields.
     */
    public Statistics toModelType() throws IllegalArgumentException {
        return new Statistics(this.consultations, this.revenue, this.expenditure, this.medicines, this.symptoms);
    }
}
