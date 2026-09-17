package campus.queue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single student service request placed in the queue
 * (Requirement 4: queue to manage service requests in arrival order).
 */
public class ServiceRequest {

    private String studentId;
    private String requestDetails;
    private LocalDateTime timestamp;

    public ServiceRequest(String studentId, String requestDetails) {
        this.studentId = studentId;
        this.requestDetails = requestDetails;
        this.timestamp = LocalDateTime.now();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getRequestDetails() {
        return requestDetails;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "Student ID: " + studentId + " | Request: " + requestDetails
                + " | Time: " + timestamp.format(fmt);
    }
}
