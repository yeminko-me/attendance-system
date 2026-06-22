package attendance.dao.model;

public record Employee(int id, String username, String password, String fullName, String role, String address, String phone) {
    public Employee(int id, String username, String fullName, String role, String address, String phone) {
        this(id, username, null, fullName, role, address, phone);
    }

    @Override
    public String toString() {
        return id + " - " + fullName;
    }
}
