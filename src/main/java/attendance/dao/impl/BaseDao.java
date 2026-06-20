package attendance.dao.impl;

import java.sql.Connection;

public class BaseDao {
    protected DAO dao = DAO.getSingleton();
    protected Connection con = dao.getConnection();

    public void deleteById(String tableName, int id) {
        String sql = "DELETE FROM " + tableName + " WHERE id=?";
        try (var stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
