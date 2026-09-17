package campus.tree;

import campus.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Self-balancing AVL tree that organizes student records by Student ID
 * (Requirement 5: BST or AVL tree to organize/search records by ID).
 *
 * Every insertion/deletion keeps the balance factor of each node within
 * [-1, +1] using the standard LL / RR / LR / RL rotations.
 */
public class StudentAVLTree {

    private AVLNode root;

    private int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    private int balanceFactor(AVLNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private void updateHeight(AVLNode node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode t2 = x.right;

        x.right = y;
        y.left = t2;

        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode t2 = y.left;

        y.left = x;
        x.right = t2;

        updateHeight(x);
        updateHeight(y);
        return y;
    }

    /** Inserts a student, or updates it in place if the ID already exists. */
    public void insert(Student student) {
        root = insertRec(root, student);
    }

    private AVLNode insertRec(AVLNode node, Student student) {
        if (node == null) {
            return new AVLNode(student);
        }

        int cmp = student.getStudentId().compareToIgnoreCase(node.data.getStudentId());
        if (cmp < 0) {
            node.left = insertRec(node.left, student);
        } else if (cmp > 0) {
            node.right = insertRec(node.right, student);
        } else {
            node.data = student; // duplicate ID -> update existing record
            return node;
        }

        updateHeight(node);
        int balance = balanceFactor(node);

        // LL case
        if (balance > 1 && student.getStudentId().compareToIgnoreCase(node.left.data.getStudentId()) < 0) {
            return rotateRight(node);
        }
        // RR case
        if (balance < -1 && student.getStudentId().compareToIgnoreCase(node.right.data.getStudentId()) > 0) {
            return rotateLeft(node);
        }
        // LR case
        if (balance > 1 && student.getStudentId().compareToIgnoreCase(node.left.data.getStudentId()) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // RL case
        if (balance < -1 && student.getStudentId().compareToIgnoreCase(node.right.data.getStudentId()) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    /** Removes a student by ID, rebalancing the tree afterwards. */
    public void remove(String studentId) {
        root = removeRec(root, studentId);
    }

    private AVLNode removeRec(AVLNode node, String studentId) {
        if (node == null) return null;

        int cmp = studentId.compareToIgnoreCase(node.data.getStudentId());
        if (cmp < 0) {
            node.left = removeRec(node.left, studentId);
        } else if (cmp > 0) {
            node.right = removeRec(node.right, studentId);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                AVLNode successor = minValueNode(node.right);
                node.data = successor.data;
                node.right = removeRec(node.right, successor.data.getStudentId());
            }
        }

        if (node == null) return null;

        updateHeight(node);
        int balance = balanceFactor(node);

        if (balance > 1 && balanceFactor(node.left) >= 0) return rotateRight(node);
        if (balance > 1 && balanceFactor(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && balanceFactor(node.right) <= 0) return rotateLeft(node);
        if (balance < -1 && balanceFactor(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) current = current.left;
        return current;
    }

    /** Searches for a student by ID in O(log n) time. */
    public Student search(String studentId) {
        AVLNode node = root;
        while (node != null) {
            int cmp = studentId.compareToIgnoreCase(node.data.getStudentId());
            if (cmp == 0) return node.data;
            node = (cmp < 0) ? node.left : node.right;
        }
        return null;
    }

    /** Displays every student in ascending Student ID order. */
    public void displayInOrder() {
        List<Student> list = new ArrayList<>();
        inOrder(root, list);
        if (list.isEmpty()) {
            System.out.println("No student records in the tree.");
            return;
        }
        System.out.println("--- Students sorted by Student ID (AVL in-order traversal) ---");
        for (Student s : list) {
            System.out.println(s);
        }
    }

    private void inOrder(AVLNode node, List<Student> list) {
        if (node == null) return;
        inOrder(node.left, list);
        list.add(node.data);
        inOrder(node.right, list);
    }

    public boolean isEmpty() {
        return root == null;
    }
}
