package campus.menu;

import campus.graph.CampusGraph;
import campus.hashtable.StudentHashTable;
import campus.linkedlist.StudentLinkedList;
import campus.model.Student;
import campus.queue.ServiceQueue;
import campus.queue.ServiceRequest;
import campus.stack.Action;
import campus.stack.ActionStack;
import campus.tree.StudentAVLTree;
import campus.util.InputValidator;
import java.util.List;
import java.util.Scanner;

/**
 * Menu-driven console interface (Requirement 13) that wires together every
 * data structure required by the assignment: linked list, stack, queue,
 * AVL tree, hash table, and graph.
 */
public class ConsoleMenu {

    private final Scanner sc;
    private final StudentLinkedList studentList;
    private final ActionStack actionStack;
    private final ServiceQueue serviceQueue;
    private final StudentAVLTree studentTree;
    private final StudentHashTable studentHashTable;
    private final CampusGraph campusGraph;

    public ConsoleMenu() {
        sc = new Scanner(System.in);
        studentList = new StudentLinkedList();
        actionStack = new ActionStack(50);
        serviceQueue = new ServiceQueue(20);
        studentTree = new StudentAVLTree();
        studentHashTable = new StudentHashTable(101); // prime capacity reduces collisions
        campusGraph = new CampusGraph();
    }

    public void start() {
        boolean running = true;
        System.out.println("=========================================================");
        System.out.println(" UNIVERSITY STUDENT RECORD & CAMPUS ROUTE MANAGEMENT SYSTEM");
        System.out.println("=========================================================");

        while (running) {
            printMenu();
            int choice = InputValidator.readValidInt(sc, "Enter your choice: ", 1, 16);

            switch (choice) {
                case 1: addStudentRecord(); break;
                case 2: updateStudentRecord(); break;
                case 3: deleteStudentRecord(); break;
                case 4: studentList.displayAll(); break;
                case 5: addServiceRequest(); break;
                case 6: processNextServiceRequest(); break;
                case 7: actionStack.displayRecentActions(10); break;
                case 8: studentTree.displayInOrder(); break;
                case 9: searchStudentUsingHashing(); break;
                case 10: addCampusLocation(); break;
                case 11: removeCampusLocation(); break;
                case 12: addCampusConnection(); break;
                case 13: removeCampusConnection(); break;
                case 14: campusGraph.displayConnections(); break;
                case 15: traverseCampus(); break;
                case 16:
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
            System.out.println();
        }
        sc.close();
    }

    private void printMenu() {
        System.out.println("--------------------------------------------------------");
        System.out.println("1.  Add Student Record");
        System.out.println("2.  Update Student Record");
        System.out.println("3.  Delete Student Record");
        System.out.println("4.  Display All Records using Linked List");
        System.out.println("5.  Add Service Request to Queue");
        System.out.println("6.  Process Next Service Request");
        System.out.println("7.  Display Recent Actions using Stack");
        System.out.println("8.  Display Students using BST/AVL");
        System.out.println("9.  Search Student using Hashing");
        System.out.println("10. Add Campus Location");
        System.out.println("11. Remove Campus Location");
        System.out.println("12. Add Campus Connection/Road");
        System.out.println("13. Remove Campus Connection/Road");
        System.out.println("14. Display Campus Connections");
        System.out.println("15. Traverse Campus Locations using BFS or DFS");
        System.out.println("16. Exit");
        System.out.println("--------------------------------------------------------");
    }

    // ---------------- Student Record Operations (Linked List + Tree + Hash Table) ----------------

    private void addStudentRecord() {
        String id = InputValidator.readNonEmptyString(sc, "Enter Student ID: ");

        if (studentList.exists(id)) {
            System.out.println("Error: A student with ID " + id + " already exists.");
            return;
        }

        String name = InputValidator.readNonEmptyString(sc, "Enter Name: ");
        String programme = InputValidator.readNonEmptyString(sc, "Enter Programme: ");
        double marks = InputValidator.readValidMarks(sc, "Enter Marks (0-100): ");

        Student student = new Student(id, name, programme, marks);
        studentList.addStudent(student);
        studentTree.insert(student);
        studentHashTable.insert(student);

        actionStack.push(new Action(Action.Type.ADD, "Added student " + id + " (" + name + ")", null));
        System.out.println("Student record added successfully.");
    }

    private void updateStudentRecord() {
        String id = InputValidator.readNonEmptyString(sc, "Enter Student ID to update: ");
        Student existing = studentList.searchStudent(id);

        if (existing == null) {
            System.out.println("Error: No student found with ID " + id);
            return;
        }

        System.out.println("Current record: " + existing);
        System.out.println("Leave a field blank to keep its current value.");

        System.out.print("Enter new Name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter new Programme: ");
        String programme = sc.nextLine().trim();

        System.out.print("Enter new Marks (0-100, or leave blank): ");
        String marksInput = sc.nextLine().trim();
        Double marks = null;
        if (!marksInput.isEmpty()) {
            try {
                double parsed = Double.parseDouble(marksInput);
                if (Double.isNaN(parsed) || Double.isInfinite(parsed) || parsed < 0 || parsed > 100) {
                    System.out.println("Invalid marks value (must be 0-100); keeping original.");
                } else {
                    marks = parsed;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid marks format; keeping original.");
            }
        }

        studentList.updateStudent(id, name.isEmpty() ? null : name,
                programme.isEmpty() ? null : programme, marks);

        // keep the tree and hash table in sync with the updated record
        Student updated = studentList.searchStudent(id);
        studentTree.insert(updated);
        studentHashTable.insert(updated);

        actionStack.push(new Action(Action.Type.UPDATE, "Updated student " + id, null));
        System.out.println("Student record updated successfully.");
    }

    private void deleteStudentRecord() {
        String id = InputValidator.readNonEmptyString(sc, "Enter Student ID to delete: ");
        Student removed = studentList.deleteStudent(id);

        if (removed == null) {
            System.out.println("Error: No student found with ID " + id);
            return;
        }

        studentTree.remove(id);
        studentHashTable.remove(id);

        actionStack.push(new Action(Action.Type.DELETE,
                "Deleted student " + id + " (" + removed.getName() + ")", removed));
        System.out.println("Student record deleted successfully.");
    }

    // ---------------- Queue Operations ----------------

    private void addServiceRequest() {
        String id = InputValidator.readNonEmptyString(sc, "Enter Student ID for the request: ");
        String details = InputValidator.readNonEmptyString(sc, "Enter Request Details: ");

        serviceQueue.insert(new ServiceRequest(id, details));
        actionStack.push(new Action(Action.Type.ADD, "Service request queued for " + id, null));
        System.out.println("Service request added to queue.");
    }

    private void processNextServiceRequest() {
        ServiceRequest request = serviceQueue.remove();
        if (request == null) {
            System.out.println("No pending service requests to process.");
            return;
        }
        System.out.println("Processing request -> " + request);
        actionStack.push(new Action(Action.Type.UPDATE,
                "Processed service request for " + request.getStudentId(), null));
    }

    // ---------------- Hashing Operations ----------------

    private void searchStudentUsingHashing() {
        String id = InputValidator.readNonEmptyString(sc, "Enter Student ID to search: ");
        Student result = studentHashTable.search(id);
        if (result == null) {
            System.out.println("No student found with ID " + id);
        } else {
            System.out.println("Found: " + result);
        }
    }

    // ---------------- Graph Operations ----------------

    private void addCampusLocation() {
        String name = InputValidator.readNonEmptyString(sc, "Enter Campus Location name: ");
        if (campusGraph.addLocation(name)) {
            System.out.println("Location added: " + name);
        } else {
            System.out.println("Error: Location \"" + name + "\" already exists.");
        }
    }

    private void removeCampusLocation() {
        String name = InputValidator.readNonEmptyString(sc, "Enter Campus Location name to remove: ");
        if (campusGraph.removeLocation(name)) {
            System.out.println("Location removed: " + name);
        } else {
            System.out.println("Error: Location \"" + name + "\" not found.");
        }
    }

    private void addCampusConnection() {
        String from = InputValidator.readNonEmptyString(sc, "Enter first location: ");
        String to = InputValidator.readNonEmptyString(sc, "Enter second location: ");
        if (campusGraph.addConnection(from, to)) {
            System.out.println("Connection added between " + from + " and " + to);
        } else {
            System.out.println("Error: could not add connection "
                    + "(check that both locations exist and are not already connected).");
        }
    }

    private void removeCampusConnection() {
        String from = InputValidator.readNonEmptyString(sc, "Enter first location: ");
        String to = InputValidator.readNonEmptyString(sc, "Enter second location: ");
        if (campusGraph.removeConnection(from, to)) {
            System.out.println("Connection removed between " + from + " and " + to);
        } else {
            System.out.println("Error: connection not found.");
        }
    }

    private void traverseCampus() {
        if (campusGraph.isEmpty()) {
            System.out.println("No campus locations available to traverse.");
            return;
        }
        String start = InputValidator.readNonEmptyString(sc, "Enter starting location: ");
        if (!campusGraph.hasLocation(start)) {
            System.out.println("Error: Location \"" + start + "\" not found.");
            return;
        }

        System.out.println("Choose traversal type: 1. BFS   2. DFS");
        int type = InputValidator.readValidInt(sc, "Enter choice: ", 1, 2);

        List<String> result = (type == 1) ? campusGraph.bfs(start) : campusGraph.dfs(start);
        String label = (type == 1) ? "BFS" : "DFS";

        System.out.println(label + " Traversal from " + start + ": " + result);
    }
}
