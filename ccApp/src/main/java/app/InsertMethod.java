package app;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.swing.*;

public class InsertMethod {


    public static void product(String name, String unit) throws SQLException {
        Connection conn = ConnectionClass.getConnection();
        String query = String.format(
                "INSERT INTO produkty (nazwa, jednostka) VALUES('%s', '%s');",
                name, unit);
        System.out.print(query);
        System.out.println();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);

        } catch (SQLException e) {
           // e.printStckTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        conn.close();
    }


    public static void productHibernate(String name, String unit, EntityManagerFactory emf) throws SQLException {

        System.out.println("Zapis obiektów do bazy...");
        EntityManager entityManager = emf.createEntityManager();

        // Check if the product already exists in the database
        TypedQuery<ProductHibernate> query = entityManager.createQuery("SELECT p FROM Produkt p WHERE p.nazwa = :nazwa AND p.jednostka = :jednostka", ProductHibernate.class);
        query.setParameter("nazwa", name);
        query.setParameter("jednostka", unit);


        List<ProductHibernate> existingProducts = query.getResultList();

        if (existingProducts.isEmpty()) {
            // Product does not exist, proceed with saving
            entityManager.getTransaction().begin();

            ProductHibernate product = new ProductHibernate();
            product.setName(name);
            product.setUnit(unit);

            entityManager.persist(product);

            entityManager.getTransaction().commit();
            System.out.println("Product saved successfully!");
        } else {
            // Product with the same name and unit already exists
            System.out.println("Product already exists in the database.");
            JOptionPane.showMessageDialog(null, "Product already exists in the database.", "Duplicate Product", JOptionPane.INFORMATION_MESSAGE);
        }

        entityManager.close();
    }


    public static void recipe(String name, String preparation) throws SQLException {
        Connection conn = ConnectionClass.getConnection();
        String query = String.format(
                "INSERT INTO przepisy (nazwa, sposob_przygotowania) VALUES('%s', '%s');",
                name, preparation);
        System.out.print(query);
        System.out.println();
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);

        } catch (SQLException e) {
            //e.printStckTrace();
        } finally {
            if (stmt != null) { stmt.close(); }
        }
        conn.close();
    }

//    public static void productRecipe(String name, String preparation, List<String> ingredientsList) throws SQLException {
//        Connection conn = ConnectionClass.getConnection();
//        int recipeId = insertRecipeAndGetId(name, preparation);
//
//        for (String ingredientWithQuantity : ingredientsList) {
//
//            if (ingredientWithQuantity.contains("(") && ingredientWithQuantity.contains(")")) {
//                int start = ingredientWithQuantity.indexOf("(") + 1;
//                int end = ingredientWithQuantity.indexOf(")");
//
//                if (start < end) {
//                    String quantityString = ingredientWithQuantity.substring(start, end);
//
//                    String ingredientName = ingredientWithQuantity.substring(0, start - 1).trim();
//
//                    try {
//                        int quantity = Integer.parseInt(quantityString);
//
//                        int ingredientId = getIngredientIdByName(ingredientName);
//                        insertRecipeIngredient(recipeId, ingredientId, quantity);
//                    } catch (NumberFormatException e) {
//                        System.err.println("Błędny format ilości dla składnika: " + ingredientWithQuantity);
//                    }
//                } else {
//                    System.err.println("Błędny format składnika: " + ingredientWithQuantity);
//                }
//            } else {
//                String ingredientName = ingredientWithQuantity.trim();
//                int ingredientId = getIngredientIdByName(ingredientName);
//                insertRecipeIngredient(recipeId, ingredientId, 1);
//            }
//        }
//        conn.close();
//    }
public static void productRecipe(String name, String preparation, List<String> ingredientsList) throws SQLException {
    Connection conn = ConnectionClass.getConnection();
    int recipeId = insertRecipeAndGetId(name, preparation);

    for (String ingredientWithQuantity : ingredientsList) {
        String ingredientName;
        int quantity = 1; // Domyślna ilość, gdy nie podano

        // Sprawdź czy podana ilość w nawiasach
        if (ingredientWithQuantity.contains("(") && ingredientWithQuantity.contains(")") && ingredientWithQuantity.contains("{")) {
            int start = ingredientWithQuantity.indexOf("(") + 1;
            int end = ingredientWithQuantity.indexOf(")");
            int start1 = ingredientWithQuantity.indexOf("{");

            if (start < end) {
                String quantityString = ingredientWithQuantity.substring(start, end).trim();

                // Spróbuj parsować ilość
                try {
                    quantity = Integer.parseInt(quantityString);
                } catch (NumberFormatException e) {
                    System.err.println("Błędny format ilości dla składnika: " + ingredientWithQuantity);
                }

                // Pobierz nazwę składnika
                ingredientName = ingredientWithQuantity.substring(0, start1).trim();
            } else {
                System.err.println("Błędny format składnika: " + ingredientWithQuantity);
                continue; // Kontynuuj z następnym składnikiem
            }
        } else {
            // Jeśli brak nawiasów, pobierz nazwę składnika bez ilości
            ingredientName = ingredientWithQuantity.trim();
        }

        // Uzyskaj id składnika
        int ingredientId = getIngredientIdByName(ingredientName);

        // Wstaw składnik do przepisu
        insertRecipeIngredient(recipeId, ingredientId, quantity);
    }

    conn.close();
}



    private static int insertRecipeAndGetId(String name, String preparation) throws SQLException {
        Connection conn = ConnectionClass.getConnection();
        String query = String.format(
                "INSERT INTO przepisy (nazwa, sposob_przygotowania) VALUES('%s', '%s');", name, preparation);
        System.out.println(query);
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Nie udało się uzyskać ID nowo wstawionego przepisu.");
                }
            }
        }
    }

    private static int getIngredientIdByName(String ingredientName) throws SQLException {
        Connection conn = ConnectionClass.getConnection();
        String query = String.format("SELECT id_produkty FROM produkty WHERE nazwa = '%s';", ingredientName);
        try (Statement stmt = conn.createStatement(); ResultSet resultSet = stmt.executeQuery(query)) {
            if (resultSet.next()) {
                return resultSet.getInt("id_produkty");
            } else {
                throw new SQLException("Nie udało się uzyskać ID składnika o nazwie: " + ingredientName);
            }
        }
    }

    private static void insertRecipeIngredient(int recipeId, int ingredientId, int quantity) throws SQLException {
        Connection conn = ConnectionClass.getConnection();
        String query = String.format(
                "INSERT INTO przepisy_produkty (przepisy_id_przepisy, produkty_id_produkty, ilosc) VALUES ('%d', '%d', '%d');",
                recipeId, ingredientId, quantity);
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        }
        conn.close();
    }
}
