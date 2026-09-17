# University Student Record and Campus Route Management System

**Module:** CIT300 – Data Structures and Algorithms
**Assignment:** Graded Practical Assignment 1 (Week 10) — 10% of final grade
**Institution:** Sri Lanka Technology Campus (SLTC), Bachelor of Applied Information Technology

## Overview

A Java console application that manages university student records and models
campus locations/connections as a graph. Demonstrates the practical use of a
linked list, stack, queue, AVL tree, hash table, and graph within one system.

## Group Members

| Name | Student ID | Email | Responsibility |
|---|---|---|---|
| RM. Almunthazeer | 23DA2-1148 | 23da2-1148@sltc.ac.lk | Linked list implementation and student-record management |
| ABF. Sarafa | 23DA2-1167 | 23da2-1167@sltc.ac.lk | Stack and queue implementation and related operations |
| RF. Riska | 23DA2-1137 | 23da2-1137@sltc.ac.lk | AVL tree implementation and hashing/search functionality |
| JF. Sharafa | 23DA2-1117 | 23da2-1117@sltc.ac.lk | Graph implementation, campus locations/connections, BFS/DFS traversal |

*All members: integration, validation, testing, debugging, documentation, GitHub collaboration.*

Individual contribution notes: see `docs/group-contribution.md`.
GitHub branching/PR workflow for the team: see `GITHUB_WORKFLOW.md`.

## Data Structures Used

| Requirement | Data Structure | Class(es) |
|---|---|---|
| Store & manage student records | Singly Linked List | `campus.linkedlist.StudentLinkedList` |
| Recent actions / undo history | Array-based Stack | `campus.stack.ActionStack` |
| Service requests (arrival order) | Circular Array Queue | `campus.queue.ServiceQueue` |
| Organize/search records by ID | AVL Tree (self-balancing BST) | `campus.tree.StudentAVLTree` |
| Efficient Student ID search | Hash Table (separate chaining) | `campus.hashtable.StudentHashTable` |
| Campus locations & connections | Graph (adjacency list, BFS/DFS) | `campus.graph.CampusGraph` |

## Project Structure

```
CIT300-Student-Campus-Management-System/
├── src/campus/
│   ├── Main.java
│   ├── model/Student.java
│   ├── linkedlist/StudentNode.java, StudentLinkedList.java
│   ├── stack/Action.java, ActionStack.java
│   ├── queue/ServiceRequest.java, ServiceQueue.java
│   ├── tree/AVLNode.java, StudentAVLTree.java
│   ├── hashtable/StudentHashTable.java
│   ├── graph/CampusGraph.java
│   ├── menu/ConsoleMenu.java
│   └── util/InputValidator.java
├── docs/group-contribution.md
├── demo/demo-video-link.txt
└── README.md
```

## How to Compile and Run

From the project root:

```bash
javac -d bin $(find src -name "*.java")
java -cp bin campus.Main
```

Or import the `src` folder as a Java project in Eclipse / IntelliJ and run `campus.Main`.

## Menu

```
1.  Add Student Record
2.  Update Student Record
3.  Delete Student Record
4.  Display All Records using Linked List
5.  Add Service Request to Queue
6.  Process Next Service Request
7.  Display Recent Actions using Stack
8.  Display Students using BST/AVL
9.  Search Student using Hashing
10. Add Campus Location
11. Remove Campus Location
12. Add Campus Connection/Road
13. Remove Campus Connection/Road
14. Display Campus Connections
15. Traverse Campus Locations using BFS or DFS
16. Exit
```

## Input Validation & Error Handling

- Duplicate Student IDs are rejected on add.
- Marks are validated to be numeric and within 0–100.
- Menu choices are validated to be within the valid range.
- Missing records / locations / connections return clear error messages
  instead of crashing.
- Graph connections require both locations to already exist.

## Notes

- The AVL tree satisfies the "BST or AVL tree" requirement while also keeping
  student records auto-balanced (rotations covered in Week 7 lectures).
- The campus graph is undirected (a road connects both ways) and implemented
  with an adjacency list (`HashMap<String, List<String>>`).
- Both BFS and DFS traversals are implemented; the menu lets the user choose
  which one to run.
