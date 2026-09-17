package campus.stack;

import campus.model.Student;

/**
 * Represents a single recorded action (add/update/delete) for the
 * recent-actions / undo history stack (Requirement 3).
 */
public class Action {

    public enum Type { ADD, UPDATE, DELETE }

    private Type type;
    private String description;
    private Student snapshot; // holds the deleted student, so a delete could be undone if needed

    public Action(Type type, String description, Student snapshot) {
        this.type = type;
        this.description = description;
        this.snapshot = snapshot;
    }

    public Type getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Student getSnapshot() {
        return snapshot;
    }

    @Override
    public String toString() {
        return "[" + type + "] " + description;
    }
}
