package campus.graph;

import java.util.*;

/**
 * Represents the university campus as an undirected graph.
 * Locations are vertices; roads/paths/connections are edges.
 * Implemented using an adjacency list (Requirements 7-11).
 */
public class CampusGraph {

    // location name -> list of directly connected location names
    private Map<String, List<String>> adjacencyList;

    public CampusGraph() {
        adjacencyList = new LinkedHashMap<>();
    }

    /** Adds a new campus location (vertex). Returns false if it already exists. */
    public boolean addLocation(String name) {
        if (adjacencyList.containsKey(name)) {
            return false;
        }
        adjacencyList.put(name, new ArrayList<>());
        return true;
    }

    /** Removes a location and every connection referencing it. */
    public boolean removeLocation(String name) {
        if (!adjacencyList.containsKey(name)) {
            return false;
        }
        adjacencyList.remove(name);
        for (List<String> neighbours : adjacencyList.values()) {
            neighbours.remove(name);
        }
        return true;
    }

    /** Adds an undirected connection/road between two existing locations. */
    public boolean addConnection(String from, String to) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            return false; // one or both locations don't exist
        }
        if (adjacencyList.get(from).contains(to)) {
            return false; // already connected
        }
        adjacencyList.get(from).add(to);
        adjacencyList.get(to).add(from);
        return true;
    }

    /** Removes the connection/road between two locations. */
    public boolean removeConnection(String from, String to) {
        if (!adjacencyList.containsKey(from) || !adjacencyList.containsKey(to)) {
            return false;
        }
        boolean removed1 = adjacencyList.get(from).remove(to);
        boolean removed2 = adjacencyList.get(to).remove(from);
        return removed1 || removed2;
    }

    public boolean hasLocation(String name) {
        return adjacencyList.containsKey(name);
    }

    public boolean isEmpty() {
        return adjacencyList.isEmpty();
    }

    /** Displays the full campus network as an adjacency list. */
    public void displayConnections() {
        if (adjacencyList.isEmpty()) {
            System.out.println("No campus locations added yet.");
            return;
        }
        System.out.println("--- Campus Network (Adjacency List) ---");
        for (Map.Entry<String, List<String>> entry : adjacencyList.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }

    /** Breadth-First Search traversal starting from the given location. */
    public List<String> bfs(String start) {
        List<String> visitOrder = new ArrayList<>();
        if (!adjacencyList.containsKey(start)) {
            return visitOrder;
        }
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            visitOrder.add(current);

            for (String neighbour : adjacencyList.get(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        return visitOrder;
    }

    /** Depth-First Search traversal starting from the given location. */
    public List<String> dfs(String start) {
        List<String> visitOrder = new ArrayList<>();
        if (!adjacencyList.containsKey(start)) {
            return visitOrder;
        }
        Set<String> visited = new HashSet<>();
        dfsHelper(start, visited, visitOrder);
        return visitOrder;
    }

    private void dfsHelper(String current, Set<String> visited, List<String> visitOrder) {
        visited.add(current);
        visitOrder.add(current);
        for (String neighbour : adjacencyList.get(current)) {
            if (!visited.contains(neighbour)) {
                dfsHelper(neighbour, visited, visitOrder);
            }
        }
    }

    public Set<String> getAllLocations() {
        return adjacencyList.keySet();
    }
}
