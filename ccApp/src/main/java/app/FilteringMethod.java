package app;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class FilteringMethod {
    public static void filteringMethod(List<String> ingredientsList, JTable table) throws SQLException {
        Connection conn = ConnectionClass.getConnection();
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT p.id_przepisy, p.nazwa, p.sposob_przygotowania FROM przepisy p ");

            for (int i = 0; i < ingredientsList.size(); i++) {
                queryBuilder.append("JOIN przepisy_produkty pp").append(i).append(" ON p.id_przepisy = pp").append(i).append(".przepisy_id_przepisy ");
                queryBuilder.append("JOIN produkty prod").append(i).append(" ON pp").append(i).append(".produkty_id_produkty = prod").append(i).append(".id_produkty ");
            }

            queryBuilder.append("WHERE prod0.nazwa = ?");
            for (int i = 1; i < ingredientsList.size(); i++) {
                queryBuilder.append(" AND prod").append(i).append(".nazwa = ?");
            }

            try (PreparedStatement preparedStatement = conn.prepareStatement(queryBuilder.toString())) {
                int parameterIndex = 1;
                for (String ingredient : ingredientsList) {
                    preparedStatement.setString(parameterIndex, ingredient);
                    parameterIndex++;
                }

                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    DefaultTableModel model = new DefaultTableModel();
                    model.addColumn("ID");
                    model.addColumn("Nazwa");
                    model.addColumn("Sposób przygotowania");


                    while (resultSet.next()) {
                        Vector<Object> row = new Vector<>();
                        row.add(resultSet.getInt("id_przepisy"));
                        row.add(resultSet.getString("nazwa"));
                        row.add(resultSet.getString("sposob_przygotowania"));
                        model.addRow(row);
                    }


                    table.setModel(model);
                    TableOptions.tableOptions(table);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
    }
}
