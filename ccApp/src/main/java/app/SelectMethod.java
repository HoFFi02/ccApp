package app;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class SelectMethod {

    public static ArrayList<Recipe> getRecipesAll() throws IOException, SQLException {
        Connection conn = ConnectionClass.getConnection();
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        Statement stmt = null;
        String query = "SELECT * FROM przepisy";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Recipe recipe = new Recipe(rs.getInt(1), rs.getString(2), rs.getString(3));
                recipes.add(recipe);
            }

            return recipes;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return null;
    }

    public static ArrayList<Product> getProductsAll() throws IOException, SQLException {
        Connection conn = ConnectionClass.getConnection();
        ArrayList<Product> products = new ArrayList<Product>();
        Statement stmt = null;
        String query = "SELECT * FROM produkty";

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Product product = new Product(rs.getInt(1), rs.getString(2), rs.getString(3));
                products.add(product);

            }
            return products;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        conn.close();
        return null;
    }

    public static List<ProductHibernate> getProductsAllHibernate(EntityManagerFactory emf) {


        EntityManager entityManager = emf.createEntityManager();


        System.out.println("Pobieranie danych z bazy - cała tabela");
        entityManager.getTransaction().begin();


        List<ProductHibernate> result = entityManager.createQuery("from Produkt where id > 0", ProductHibernate.class).getResultList();
        for (ProductHibernate event : result) {
            System.out.println("Message (" + event.getId() + ") : " + event.getName());
        }


        entityManager.getTransaction().commit();
        entityManager.close();


       // System.out.println("Zamkniecie sesji");

        for (ProductHibernate fr : result) {
            System.out.println(fr);
        }
//
        return  result;
    }
}
