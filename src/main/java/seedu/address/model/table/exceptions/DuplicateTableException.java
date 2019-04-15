package seedu.address.model.table.exceptions;

/**
 * Signals that the operation will result in duplicate Table (Tables are considered duplicates if they
 * have the same identity or their TableNumbers are the same).
 */
public class DuplicateTableException extends RuntimeException {}
