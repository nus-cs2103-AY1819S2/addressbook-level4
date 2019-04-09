package quickdocs.testutil;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import quickdocs.model.QuickDocs;
import quickdocs.model.consultation.Assessment;
import quickdocs.model.consultation.Diagnosis;
import quickdocs.model.consultation.Prescription;
import quickdocs.model.consultation.Symptom;
import quickdocs.model.medicine.Medicine;
import quickdocs.model.record.ConsultationRecord;
import quickdocs.model.record.MedicinePurchaseRecord;
import quickdocs.model.record.MonthStatistics;
import quickdocs.model.record.Record;
import quickdocs.model.record.StatisticsManager;

/**
 * A utility class containing sample {@code MonthStatistics} objects to be used in tests
 */
public class TypicalStatistics {
    private static final StatisticsManager SAMPLE_STATISTICS_MANAGER_CONSULTFEE_30 = new StatisticsManager();
    private static final Medicine SAMPLE_MEDICINE_PARACETAMOL = new Medicine("paracetamol");
    private static final Medicine SAMPLE_MEDICINE_DEXTROMETHORPHAN = new Medicine("dextromethorphan");
    private static final Prescription SAMPLE_PRESCRIPTION_A = new Prescription(SAMPLE_MEDICINE_PARACETAMOL, 1);
    private static final Prescription SAMPLE_PRESCRIPTION_B = new Prescription(SAMPLE_MEDICINE_DEXTROMETHORPHAN, 2);
    private static final List<Prescription> SAMPLE_PRESCRIPTIONS_A = List.of(SAMPLE_PRESCRIPTION_A);
    private static final List<Prescription> SAMPLE_PRESCRIPTIONS_B = List.of(SAMPLE_PRESCRIPTION_B);
    private static final Assessment SAMPLE_ASSESSMENT = new Assessment("Test Assessment");
    private static final Symptom SAMPLE_SYMPTOM_COUGH = new Symptom("cough");
    private static final Symptom SAMPLE_SYMPTOM_FEVER = new Symptom("fever");
    private static final List<Symptom> SAMPLE_SYMPTOMS_A = List.of(SAMPLE_SYMPTOM_COUGH);
    private static final List<Symptom> SAMPLE_SYMPTOMS_B = List.of(SAMPLE_SYMPTOM_COUGH, SAMPLE_SYMPTOM_FEVER);
    private static final Diagnosis SAMPLE_DIAGNOSIS_A = new Diagnosis(SAMPLE_ASSESSMENT,
            new ArrayList<>(SAMPLE_SYMPTOMS_A));
    private static final Diagnosis SAMPLE_DIAGNOSIS_B = new Diagnosis(SAMPLE_ASSESSMENT,
            new ArrayList<>(SAMPLE_SYMPTOMS_B));

    public static final Record SAMPLE_RECORD_CR_A = new ConsultationRecord(new ArrayList<>(SAMPLE_PRESCRIPTIONS_A),
            SAMPLE_DIAGNOSIS_A);
    public static final Record SAMPLE_RECORD_CR_B = new ConsultationRecord(new ArrayList<>(SAMPLE_PRESCRIPTIONS_B),
            SAMPLE_DIAGNOSIS_B);
    public static final Record SAMPLE_RECORD_MPR_A = new MedicinePurchaseRecord(SAMPLE_MEDICINE_PARACETAMOL,
            4, BigDecimal.valueOf(5));
    public static final Record SAMPLE_RECORD_MPR_B = new MedicinePurchaseRecord(SAMPLE_MEDICINE_DEXTROMETHORPHAN,
            3, BigDecimal.valueOf(10));

    public static final MonthStatistics SAMPLE_MONTH_STATISTICS_JAN_2019 = new MonthStatistics(
            YearMonth.of(2019, 01),
            SAMPLE_RECORD_MPR_A.toStatistics(SAMPLE_STATISTICS_MANAGER_CONSULTFEE_30)
                .merge(SAMPLE_RECORD_MPR_B.toStatistics(SAMPLE_STATISTICS_MANAGER_CONSULTFEE_30))
    );
    public static final MonthStatistics SAMPLE_MONTH_STATISTICS_FEB_2019 = new MonthStatistics(
            YearMonth.of(2019, 02),
            SAMPLE_RECORD_CR_A.toStatistics(SAMPLE_STATISTICS_MANAGER_CONSULTFEE_30)
                    .merge(SAMPLE_RECORD_CR_B.toStatistics(SAMPLE_STATISTICS_MANAGER_CONSULTFEE_30))
    );
    public static QuickDocs getTypicalStatisticsQuickDocs() {
        QuickDocs qd = new QuickDocs();
        for (MonthStatistics monthStatistics : getTypicalMonthStatistics()) {
            qd.getStatisticsManager().addMonthStatistics(monthStatistics);
        }
        return qd;
    }
    public static List<MonthStatistics> getTypicalMonthStatistics() {
        return new ArrayList<>(Arrays.asList(SAMPLE_MONTH_STATISTICS_JAN_2019, SAMPLE_MONTH_STATISTICS_FEB_2019));
    }
}
