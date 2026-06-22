package attendance.service.impl;

import attendance.dao.EmployeeDao;
import attendance.dao.impl.EmployeeDaoImpl;
import attendance.dao.model.Employee;
import attendance.service.EmployeeService;
import java.util.ArrayList;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao dao = new EmployeeDaoImpl();

    @Override
    public Employee login(String username, String password) {
        return dao.login(username, password);
    }

    @Override
    public Employee getById(int id) {
        return dao.getById(id);
    }

    @Override
    public ArrayList<Employee> getAll() {
        return dao.getAll();
    }

    @Override
    public boolean register(String username, String password, String fullName, String role, String address, String phone) {
        return dao.register(username, password, fullName, role, address, phone);
    }

    @Override
    public void update(Employee employee) {
        dao.update(employee);
    }

    @Override
    public void deleteById(int id) {
        dao.deleteById(id);
    }

    @Override
    public boolean registerAdmin(String username, String password, String fullName, String address, String phone) {
        return register(username, password, fullName, "admin", address, phone);
    }

    @Override
    public boolean registerUser(String username, String password, String fullName, String address, String phone) {
        return register(username, password, fullName, "user", address, phone);
    }
}
