package seedu.address.model.record;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class holds the statistics relevant to the private clinic's needs
 * A Statistics object is immutable.
 */
public class Statistics {
    private int noOfConsultations;
    private BigDecimal revenue;
    private BigDecimal expenditure;
    private BigDecimal profit;
    private HashMap<String, Integer> medicinesCount;
    private HashMap<String, Integer> symptomsCount;
    public Statistics() {
        this.noOfConsultations = 0;
        this.revenue = BigDecimal.ZERO;
        this.expenditure = BigDecimal.ZERO;
        this.profit = BigDecimal.ZERO;
        this.medicinesCount = new HashMap<>();
        this.symptomsCount = new HashMap<>();
    }
    public Statistics(int noOfConsultations, BigDecimal revenue, BigDecimal expenditure) {
        this.noOfConsultations = noOfConsultations;
        this.revenue = revenue;
        this.expenditure = expenditure;
        this.profit = revenue.subtract(expenditure);
        this.medicinesCount = new HashMap<>();
        this.symptomsCount = new HashMap<>();
    }
    public Statistics(int noOfConsultations, BigDecimal revenue, BigDecimal expenditure,
                      HashMap<String, Integer> medicinesCount, HashMap<String, Integer> symptomsCount) {
        this.noOfConsultations = noOfConsultations;
        this.revenue = revenue;
        this.expenditure = expenditure;
        this.profit = revenue.subtract(expenditure);
        this.medicinesCount = medicinesCount;
        this.symptomsCount = symptomsCount;
    }
    public Statistics(Statistics stats) {
        this.noOfConsultations = stats.getNoOfConsultations();
        this.revenue = stats.getRevenue();
        this.expenditure = stats.getExpenditure();
        this.profit = stats.getProfit();
        this.medicinesCount = stats.getMedicinesCount();
        this.symptomsCount = stats.getSymptomsCount();
    }
    public int getNoOfConsultations() {
        return noOfConsultations;
    }
    public BigDecimal getRevenue() {
        return revenue;
    }
    public BigDecimal getExpenditure() {
        return expenditure;
    }
    public BigDecimal getProfit() {
        return profit;
    }
    public HashMap<String, Integer> getMedicinesCount() {
        return this.medicinesCount;
    }
    public HashMap<String, Integer> getSymptomsCount() {
        return this.symptomsCount;
    }

    /**
     * Merges this Statistics object with another Statistic object
     * @param other Statistics to merge with
     * @return A new Statistics object
     */
    public Statistics merge(Statistics other) {
        int newNoOfConsultations = this.getNoOfConsultations() + other.getNoOfConsultations();
        BigDecimal newRevenue = this.getRevenue().add(other.getRevenue());
        BigDecimal newExpenditure = this.getExpenditure().add(other.getExpenditure());
        HashMap<String, Integer> newMedicinesCount = this.getMedicinesCount();
        HashMap<String, Integer> newSymptomsCount = this.getSymptomsCount();
        other.getMedicinesCount().forEach((k, v) -> newMedicinesCount.merge(k, v, Integer::sum));
        other.getSymptomsCount().forEach((k, v) -> newSymptomsCount.merge(k, v, Integer::sum));
        return new Statistics(newNoOfConsultations, newRevenue, newExpenditure, newMedicinesCount, newSymptomsCount);
    }

    private static String getMostCommonKeyFromHashMap(HashMap<String, Integer> hashMap) {
        if (hashMap.isEmpty()) {
            return "N/A";
        }
        StringBuilder sb = new StringBuilder();
        int maxValue = Collections.max(hashMap.values());
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            if (entry.getValue() == maxValue) {
                sb.append(entry.getKey())
                        .append(": ")
                        .append(entry.getValue())
                        .append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Function to format BigDecimal objects to the locale's currency format.
     * @param money BigDecimal object representing any amount of money.
     * @return A String representation of the money formatted to the locale's currency format.
     */
    public static String currencyFormat(BigDecimal money) {
        /**
         * Adapted from https://stackoverflow.com/questions/3395825/how-to-print-formatted-bigdecimal-values/8581941
         */
        return NumberFormat.getCurrencyInstance().format(money);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of consultations: ")
                .append(getNoOfConsultations())
                .append("\n\n")
                .append("Most common medicine prescribed: \n")
                .append(Statistics.getMostCommonKeyFromHashMap(medicinesCount))
                .append("\n")
                .append("Most common symptom diagnosed: \n")
                .append(Statistics.getMostCommonKeyFromHashMap(symptomsCount))
                .append("\n")
                .append("Revenue: ")
                .append(Statistics.currencyFormat(getRevenue()))
                .append("\n")
                .append("Expenditure: ")
                .append(Statistics.currencyFormat(getExpenditure()))
                .append("\n")
                .append("Profit: ")
                .append(Statistics.currencyFormat(getProfit()))
                .append("\n\n");
        return sb.toString();
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Statistics)) {
            return false;
        }
        Statistics stats = (Statistics) other;
        return this.getNoOfConsultations() == stats.getNoOfConsultations()
                && this.getRevenue().equals(stats.getRevenue())
                && this.getExpenditure().equals(stats.getExpenditure())
                && this.getProfit().equals(stats.getProfit())
                && this.getMedicinesCount().equals(stats.getMedicinesCount())
                && this.getSymptomsCount().equals(stats.getSymptomsCount());
    }
}
