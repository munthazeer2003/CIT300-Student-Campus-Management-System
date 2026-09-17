package campus.queue;

/**
 * Circular array-based queue (FIFO) that manages student service
 * requests in the order they arrive (Requirement 4).
 */
public class ServiceQueue {

    private ServiceRequest[] queueArray;
    private int maxSize;
    private int front;
    private int rear;
    private int noItems;

    public ServiceQueue(int size) {
        maxSize = size;
        queueArray = new ServiceRequest[maxSize];
        front = 0;
        rear = -1;
        noItems = 0;
    }

    public boolean isEmpty() {
        return noItems == 0;
    }

    public boolean isFull() {
        return noItems == maxSize;
    }

    /** Adds a request to the rear of the queue, growing the array if full. */
    public void insert(ServiceRequest request) {
        if (isFull()) {
            resize();
        }
        rear = (rear + 1) % maxSize;
        queueArray[rear] = request;
        noItems++;
    }

    /** Removes and returns the request at the front of the queue. */
    public ServiceRequest remove() {
        if (isEmpty()) {
            return null;
        }
        ServiceRequest temp = queueArray[front];
        queueArray[front] = null;
        front = (front + 1) % maxSize;
        noItems--;
        return temp;
    }

    /** Looks at the front request without removing it. */
    public ServiceRequest peekFront() {
        if (isEmpty()) {
            return null;
        }
        return queueArray[front];
    }

    private void resize() {
        int newSize = maxSize * 2;
        ServiceRequest[] newArray = new ServiceRequest[newSize];
        for (int i = 0; i < noItems; i++) {
            newArray[i] = queueArray[(front + i) % maxSize];
        }
        queueArray = newArray;
        front = 0;
        rear = noItems - 1;
        maxSize = newSize;
    }

    /** Displays every pending request from front to rear. */
    public void displayAll() {
        if (isEmpty()) {
            System.out.println("No pending service requests.");
            return;
        }
        System.out.println("--- Pending Service Requests (front to rear) ---");
        int idx = front;
        for (int i = 0; i < noItems; i++) {
            System.out.println((i + 1) + ". " + queueArray[idx]);
            idx = (idx + 1) % maxSize;
        }
    }

    public int size() {
        return noItems;
    }
}
