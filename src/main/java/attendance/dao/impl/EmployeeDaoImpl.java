package attendance.dao.impl;

import attendance.dao.EmployeeDao;
import attendance.dao.model.Employee;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EmployeeDaoImpl extends BaseDao implements EmployeeDao {

    private static final String SELECT_COLS = "id, username, full_name, role, address, phone";

    @Override
    public Employee login(String username, String password) {
        String sql = "SELECT " + SELECT_COLS + " FROM employee WHERE username=? AND password=SHA2(?, 256)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapEmployee(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee getById(int id) {
        String sql = "SELECT " + SELECT_COLS + " FROM employee WHERE id=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapEmployee(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Employee> getAll() {
        var list = new ArrayList<Employee>();
        String sql = "SELECT " + SELECT_COLS + " FROM employee";
        try (var stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapEmployee(rs));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean register(String username, String password, String fullName, String role, String address, String phone) {
        String sql = "INSERT INTO employee (username, password, full_name, role, address, phone) VALUES (?, SHA2(?, 256), ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, fullName);
            stmt.setString(4, role);
            stmt.setString(5, address);
            stmt.setString(6, phone);
            stmt.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void update(Employee employee) {
        String sql = "UPDATE employee SET full_name=?, role=?, address=?, phone=? WHERE id=?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, employee.fullName());
            stmt.setString(2, employee.role());
            stmt.setString(3, employee.address());
            stmt.setString(4, employee.phone());
            stmt.setInt(5, employee.id());
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        deleteById("employee", id);
    }

    private Employee mapEmployee(ResultSet rs) throws Exception {
        return new Employee(
            rs.getInt("id"),
            rs.getString("username"),
            rs.getString("full_name"),
            rs.getString("role"),
            rs.getString("address") != null ? rs.getString("address") : "",
            rs.getString("phone") != null ? rs.getString("phone") : ""
        );
    }
}
