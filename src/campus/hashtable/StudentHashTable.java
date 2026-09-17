// Implemented by RF Riska (23DA2-1137)
package campus.hashtable;

import campus.model.Student;
import java.util.LinkedList;

/**
 * Hash table (separate chaining) supporting efficient O(1) average-case
 * searching of student records by Student ID (Requirement 6).
 */
public class StudentHashTable {

    private LinkedList<Student>[] table;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public StudentHashTable(int capacity) {
        this.capacity = capacity;
        table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new LinkedList<>();
        }
        size = 0;
    }

    /** Simple polynomial hash function based on the student ID characters. */
    private int hash(String studentId) {
        int hash = 0;
        for (char c : studentId.toUpperCase().toCharArray()) {
            hash = (hash * 31 + c) % capacity;
        }
        return Math.abs(hash);
    }

    /** Inserts a student, replacing the existing entry if the ID is already present. */
    public void insert(Student student) {
        int index = hash(student.getStudentId());
        LinkedList<Student> bucket = table[index];
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).getStudentId().equalsIgnoreCase(student.getStudentId())) {
                bucket.set(i, student);
                return;
            }
        }
        bucket.add(student);
        size++;
    }

    /** Searches for a student by ID. */
    public Student search(String studentId) {
        int index = hash(studentId);
        for (Student s : table[index]) {
            if (s.getStudentId().equalsIgnoreCase(studentId)) {
                return s;
            }
        }
        return null;
    }

    /** Removes a student by ID. Returns true if a record was removed. */
    public boolean remove(String studentId) {
        int index = hash(studentId);
        LinkedList<Student> bucket = table[index];
        for (int i = 0; i < bucket.size(); i++) {
            if (bucket.get(i).getStudentId().equalsIgnoreCase(studentId)) {
                bucket.remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    /** Displays the bucket usage of the table (useful to demonstrate hashing behaviour). */
    public void displayTable() {
        System.out.println("--- Hash Table Structure (bucket index : entry count) ---");
        for (int i = 0; i < capacity; i++) {
            if (!table[i].isEmpty()) {
                System.out.println("Bucket " + i + ": " + table[i].size()
                        + " entr" + (table[i].size() == 1 ? "y" : "ies"));
            }
        }
        System.out.println("Total entries: " + size);
    }

    public int size() {
        return size;
    }
}
