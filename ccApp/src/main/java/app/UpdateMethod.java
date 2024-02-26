package app;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateMethod {
    public static void recipe(String preparation, String name, int id) throws SQLException {
        Connection conn = ConnectionClass.getConnection();
        String query = String.format(
                "UPDATE przepisy SET sposob_przygotowania = '%s', nazwa = '%s' WHERE id_przepisy='%s';",
                preparation, name, id);
        System.out.println(query);

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        conn.close();
    }
    public static void recipeNoId(String preparation, String name) throws SQLException {
        Connection conn = ConnectionClass.getConnection();
        String query = String.format(
                "UPDATE przepisy SET sposob_przygotowania = '%s', nazwa = '%s' WHERE nazwa='%s';",
                preparation, name, name);
        System.out.println(query);

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        conn.close();
    }
}
