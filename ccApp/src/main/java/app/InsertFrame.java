package app;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static app.SelectMethod.getProductsAllHibernate;

public class InsertFrame {
    public static void product(EntityManagerFactory emf) throws IOException {
        JFrame frame = new JFrame("Dodaj Produkt");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nazwa:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(300, 25)); // Adjust size
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Jednostka:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JTextArea unitArea = new JTextArea();
        unitArea.setLineWrap(true);
        JScrollPane preparationScrollPane = new JScrollPane(unitArea);
        preparationScrollPane.setPreferredSize(new Dimension(300, 80)); // Adjust size
        panel.add(preparationScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        JButton addButton = new JButton("Dodaj Produkt");
        panel.add(addButton, gbc);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    String unit = unitArea.getText();

                 //   InsertMethod.product(name, unit);
                    InsertMethod.productHibernate(name, unit, emf);

                    nameField.setText("");
                    unitArea.setText("");


                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                MainMenu.close();
            }
        });
    }

    public static void recipe(JTable table, EntityManagerFactory emf) throws IOException, SQLException {
        JFrame frame = new JFrame("Dodaj Przepis");
        frame.setSize(600, 500); // Increase the size
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nazwa:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(300, 25));
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Sposób przygotowania:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        JTextArea preparationArea = new JTextArea();
        preparationArea.setLineWrap(true);
        JScrollPane preparationScrollPane = new JScrollPane(preparationArea);
        preparationScrollPane.setPreferredSize(new Dimension(300, 80));
        panel.add(preparationScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Składniki:"), gbc);



        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;

        List<ProductHibernate> products = getProductsAllHibernate(emf);

        String[] productNames = new String[products.size()];
        for (int i = 0; i < products.size(); i++) {
            productNames[i] = products.get(i).getName() + " {" + products.get(i).getUnit() + "}";
        }
        Arrays.sort(productNames);
        JComboBox<String> ingredientsComboBox = new JComboBox<>(productNames);
        panel.add(ingredientsComboBox, gbc);



        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        JButton addIngredientButton = new JButton("Dodaj Składnik");
        panel.add(addIngredientButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        JTextField quantityField = new JTextField();
        quantityField.setPreferredSize(new Dimension(50, 25));
        panel.add(quantityField, gbc);



        DefaultListModel<String> selectedIngredientsListModel = new DefaultListModel<>();
        JList<String> selectedIngredientsList = new JList<>(selectedIngredientsListModel);
        JScrollPane selectedIngredientsScrollPane = new JScrollPane(selectedIngredientsList);
        selectedIngredientsScrollPane.setPreferredSize(new Dimension(300, 80));

        List<String> addedIngredientsList = new ArrayList<>();

        addIngredientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedIngredient = (String) ingredientsComboBox.getSelectedItem();
                String quantity = quantityField.getText();

                if (!selectedIngredient.isEmpty()) {
                    String ingredientWithQuantity = selectedIngredient + " (" + quantity + ")";
                    selectedIngredientsListModel.addElement(ingredientWithQuantity);
                   ingredientsComboBox.removeItem(selectedIngredient);
                    quantityField.setText("");
                    addedIngredientsList.add(ingredientWithQuantity);
                }
            }
        });

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        panel.add(selectedIngredientsScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        JButton addButton = new JButton("Dodaj Przepis");
        panel.add(addButton, gbc);


        PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(new Object());
        MHdataModel statusBar = new MHdataModel(propertyChangeSupport/*, conn*/);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    String preparation = preparationArea.getText();

                    InsertMethod.productRecipe(name, preparation, addedIngredientsList);
                    statusBar.recipes = SelectMethod.getRecipesAll(/*conn*/);
                    table.setModel(statusBar);
                    TableOptions.tableOptions(table);

                    nameField.setText("");
                    preparationArea.setText("");
                    selectedIngredientsListModel.clear();
                    addedIngredientsList.clear();


                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                MainMenu.close();
            }
        });
    }
}
