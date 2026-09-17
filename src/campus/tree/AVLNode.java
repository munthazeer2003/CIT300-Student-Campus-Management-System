package campus.tree;

import campus.model.Student;

/**
 * A single node in the AVL tree, keyed by Student ID.
 */
public class AVLNode {
    Student data;
    AVLNode left, right;
    int height;

    public AVLNode(Student data) {
        this.data = data;
        this.height = 1;
    }
}
