package attendance.service;

import attendance.dao.model.Employee;
import java.util.ArrayList;

public interface EmployeeService {
    Employee login(String username, String password);
    Employee getById(int id);
    ArrayList<Employee> getAll();
    boolean register(String username, String password, String fullName, String role, String address, String phone);
    void update(Employee employee);
    void deleteById(int id);
    boolean registerAdmin(String username, String password, String fullName, String address, String phone);
    boolean registerUser(String username, String password, String fullName, String address, String phone);
}
