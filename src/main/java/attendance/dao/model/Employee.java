package attendance.dao.model;

public record Employee(int id, String username, String password, String fullName, String role) {
    public Employee(int id, String username, String fullName, String role) {
        this(id, username, null, fullName, role);
    }

    @Override
    public String toString() {
        return id + " - " + fullName;
    }
}
