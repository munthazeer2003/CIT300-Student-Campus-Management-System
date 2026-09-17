package campus.stack;

/**
 * Array-based stack (LIFO) that records recent actions performed on
 * student records, so the most recent activity/history can be displayed
 * or used for an undo feature (Requirement 3).
 */
public class ActionStack {

    private Action[] stackArray;
    private int maxSize;
    private int top;

    public ActionStack(int size) {
        maxSize = size;
        stackArray = new Action[maxSize];
        top = -1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }

    /** Pushes a new action onto the stack, growing the array if it is full. */
    public void push(Action action) {
        if (isFull()) {
            resize();
        }
        stackArray[++top] = action;
    }

    /** Removes and returns the most recently pushed action. */
    public Action pop() {
        if (isEmpty()) {
            return null;
        }
        return stackArray[top--];
    }

    /** Returns the most recent action without removing it. */
    public Action peek() {
        if (isEmpty()) {
            return null;
        }
        return stackArray[top];
    }

    private void resize() {
        int newSize = maxSize * 2;
        Action[] newArray = new Action[newSize];
        for (int i = 0; i <= top; i++) {
            newArray[i] = stackArray[i];
        }
        stackArray = newArray;
        maxSize = newSize;
    }

    /** Displays up to `count` of the most recent actions, most recent first. */
    public void displayRecentActions(int count) {
        if (isEmpty()) {
            System.out.println("No recent actions recorded.");
            return;
        }
        System.out.println("--- Recent Actions (most recent first) ---");
        int shown = 0;
        for (int i = top; i >= 0 && shown < count; i--, shown++) {
            System.out.println((shown + 1) + ". " + stackArray[i]);
        }
    }
}
