package campus.linkedlist;

import campus.model.Student;

/**
 * A single node in the singly linked list of student records.
 */
public class StudentNode {
    Student data;
    StudentNode next;

    public StudentNode(Student data) {
        this.data = data;
        this.next = null;
    }
}
