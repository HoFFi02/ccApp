package app;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteMethod {

    public static void product(String name) throws SQLException {
        Connection conn = ConnectionClass.getConnection();
        Statement stmt = null;

        String query = String.format("DELETE FROM produkty WHERE nazwa='%s';", name);

        System.out.print(query);
        System.out.println();
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
    public static void productHibernate(String name, EntityManagerFactory emf) throws SQLException {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        Integer productId = findProductIdByName(name, emf);

        if (productId != null) {
            ProductHibernate product = entityManager.find(ProductHibernate.class, productId);
            entityManager.remove(product);
            System.out.println("Produkt usunięty: " + product);
        } else {
            System.out.println("Produkt o nazwie " + name + " nie istnieje.");
        }

        entityManager.getTransaction().commit();
        entityManager.close();
    }
    public static void recipe(String name, int id) throws SQLException {
        Connection conn = ConnectionClass.getConnection();
        Statement stmt = null;

        String query = String.format("DELETE FROM przepisy_produkty WHERE przepisy_id_przepisy='%s';", id);
        String query1 = String.format("DELETE FROM dzien WHERE przepisy_id_przepisy='%s';", id);
        String query2 = String.format("DELETE FROM przepisy WHERE nazwa='%s';", name);

        System.out.print(query);
        System.out.println();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.executeUpdate(query1);
            stmt.executeUpdate(query2);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
    }
    public static void recipeNoId(String name) throws SQLException {
        Connection conn = ConnectionClass.getConnection();
        Statement stmt = null;

        String queryS=String.format("SELECT id_przepisy FROM przepisy WHERE nazwa='%s';", name);
        stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(queryS);

        int id = -1;

        if (resultSet.next()) {
            id = resultSet.getInt("id_przepisy");
        }

        String query = String.format("DELETE FROM przepisy_produkty WHERE przepisy_id_przepisy='%s';", id);
        String query1 = String.format("DELETE FROM dzien WHERE przepisy_id_przepisy='%s';", id);
        String query2 = String.format("DELETE FROM przepisy WHERE nazwa='%s';", name);

        System.out.print(query);
        System.out.println();
        try {
            stmt.executeUpdate(query);
            stmt.executeUpdate(query1);
            stmt.executeUpdate(query2);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        conn.close();
    }

    public static Integer findProductIdByName(String name, EntityManagerFactory emf) {

        EntityManager entityManager = emf.createEntityManager();

        // Użyj kwerendy JPQL do pobrania produktu na podstawie nazwy
        String jpql = "SELECT p.id FROM Produkt p WHERE p.nazwa = :name";
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        query.setParameter("name", name);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Brak produktu o podanej nazwie
            return null;
        } finally {
            entityManager.close();
        }
    }

}
