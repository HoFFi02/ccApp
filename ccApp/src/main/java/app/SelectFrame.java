package app;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectFrame {

    public static void recipe() throws IOException, SQLException {
        JFrame frame = new JFrame();
        ArrayList<Recipe> recipes = SelectMethod.getRecipesAll();

        String[] columnNames = {"ID", "Nazwa", "Sposób przygotowania"};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Recipe recipe : recipes) {
            Object[] rowData = {recipe.getId(), recipe.getName(), recipe.getPreparation()};
            model.addRow(rowData);
        }

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(2).setPreferredWidth(550);
        table.setRowHeight(100);
        table.getColumnModel().getColumn(2).setCellRenderer(new MultiLineTableCellRenderer());
        table.getColumnModel().getColumn(1).setCellRenderer(new MultiLineTableCellRenderer());
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);
        frame.setSize(750, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                MainMenu.close();
            }
        });
    }
    public static void product(EntityManagerFactory emf) throws IOException, SQLException {
        JFrame frame = new JFrame();
        {
            List<ProductHibernate> products = SelectMethod.getProductsAllHibernate(emf);

            String[] columnNames = {"ID", "Nazwa", "Jednostka"};

            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            for (ProductHibernate product : products) {
                Object[] rowData = {product.getId(), product.getName(), product.getUnit()};
                model.addRow(rowData);
            }

            JTable table = new JTable(model);
            table.getColumnModel().getColumn(1).setPreferredWidth(100);
            JScrollPane scrollPane = new JScrollPane(table);

            // Add sorting to the table
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);

            frame.add(scrollPane);
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    MainMenu.close();
                }
            });
        }
    }
    public static void days(Connection conn) throws IOException, SQLException {
        JFrame frame = new JFrame();
        {
            ArrayList<Recipe> recipes = SelectMethod.getRecipesAll();

            String[][] data = new String[recipes.size()][3];

            for (int i = 0; i < recipes.size(); i++) {
                Recipe recipe = recipes.get(i);
                data[i][0] = String.valueOf(recipe.getId());
                data[i][1] = recipe.getName();
                data[i][2] = recipe.getPreparation();
            }
            String column[] = {"ID", "Nazwa", "Sposob przygotowania"};
            JTable table = new JTable(data, column);
            table.setBounds(30, 40, 200, 300);
            JScrollPane scrollPane = new JScrollPane(table);
            frame.add(scrollPane);
            frame.setSize(300, 400);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    MainMenu.close();
                }
            });
        }
    }

}
