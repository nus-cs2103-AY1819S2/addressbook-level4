package seedu.address.model.patient;

/**
 * Interface ensures that the export teeth method returns a correct format.
 */
interface ExportableTeeth {
    /**
     * Returns a int array of 0, 1, or 2.
     * 2 - Absent tooth.
     * 1 - Problematic tooth.
     * 0 - Present and healthy tooth.
     * @return int array consisting of tooth representations.
     */
    int[] exportTeeth();
}
