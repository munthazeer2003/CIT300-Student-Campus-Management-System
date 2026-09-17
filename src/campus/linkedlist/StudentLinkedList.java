package campus.linkedlist;

import campus.model.Student;

/**
 * Singly linked list used as the primary storage structure for
 * student records (Requirement 2: linked list to store and manage records).
 */
public class StudentLinkedList {
    private StudentNode first;
    private int size;

    public StudentLinkedList() {
        first = null;
        size = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    /** Checks whether a student with the given ID already exists. */
    public boolean exists(String studentId) {
        StudentNode current = first;
        while (current != null) {
            if (current.data.getStudentId().equalsIgnoreCase(studentId)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /** Adds a new student record at the end of the list. Returns false on duplicate ID. */
    public boolean addStudent(Student student) {
        if (exists(student.getStudentId())) {
            return false;
        }
        StudentNode newNode = new StudentNode(student);
        if (first == null) {
            first = newNode;
        } else {
            StudentNode current = first;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
        return true;
    }

    /**
     * Updates an existing student's name/programme/marks.
     * Pass null for name/programme or null for marks to leave that field unchanged.
     */
    public boolean updateStudent(String studentId, String name, String programme, Double marks) {
        StudentNode current = first;
        while (current != null) {
            if (current.data.getStudentId().equalsIgnoreCase(studentId)) {
                if (name != null && !name.isEmpty()) current.data.setName(name);
                if (programme != null && !programme.isEmpty()) current.data.setProgramme(programme);
                if (marks != null) current.data.setMarks(marks);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    /** Deletes a student by ID. Returns the removed Student, or null if not found. */
    public Student deleteStudent(String studentId) {
        StudentNode current = first;
        StudentNode previous = null;

        while (current != null && !current.data.getStudentId().equalsIgnoreCase(studentId)) {
            previous = current;
            current = current.next;
        }

        if (current == null) {
            return null;
        }

        if (previous == null) {
            first = current.next;
        } else {
            previous.next = current.next;
        }
        size--;
        return current.data;
    }

    /** Linear search for a student by ID. */
    public Student searchStudent(String studentId) {
        StudentNode current = first;
        while (current != null) {
            if (current.data.getStudentId().equalsIgnoreCase(studentId)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    /** Displays every student record currently stored. */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("--------------------------------------------------------------------------------------");
        StudentNode current = first;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Total students: " + size);
    }

    /** Returns all students as an array, useful for rebuilding the tree/hash table. */
    public Student[] toArray() {
        Student[] arr = new Student[size];
        StudentNode current = first;
        int i = 0;
        while (current != null) {
            arr[i++] = current.data;
            current = current.next;
        }
        return arr;
    }
}
