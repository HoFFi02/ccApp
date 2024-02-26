package app;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static app.SelectMethod.getProductsAll;
import static app.SelectMethod.getProductsAllHibernate;

public class Filtering {

    private static DefaultTableModel originalTableModel;
 public static void filtering(JPanel panel, JTable table, EntityManagerFactory emf) throws IOException, SQLException {

     JLabel label = new JLabel("Filtrowanie: ");
     JLabel label2 = new JLabel("Wybierz produkty i wyszukaj przepis z tymi skladnikami ");
     JTextField nameField = new JTextField();
     nameField.setMaximumSize(new Dimension(150, 200));
     //ArrayList<Product> products = getProductsAll();
     List<ProductHibernate> products = getProductsAllHibernate(emf);


     DefaultComboBoxModel<String> originalComboBoxModel = new DefaultComboBoxModel<>();
   //  String[] productNames = new String[products.size()];
//     for (int i = 0; i < products.size(); i++) {
//         productNames[i] = products.get(i).getName() + " (" + products.get(i).getUnit() + ")";
//     }
//     Arrays.sort(productNames);
     for (int i = 0; i < products.size(); i++) {
         originalComboBoxModel.addElement(products.get(i).getName());
        // originalComboBoxModel.addElement(productNames[i]);
     }

     JComboBox<String> ingredientsComboBox = new JComboBox<>(originalComboBoxModel);
     ingredientsComboBox.setMaximumSize(new Dimension(150, 30));

     JButton addIngredientButton = new JButton("Dodaj Składnik");
     DefaultListModel<String> selectedIngredientsListModel = new DefaultListModel<>();
     JList<String> selectedIngredientsList = new JList<>(selectedIngredientsListModel);
     JScrollPane selectedIngredientsScrollPane = new JScrollPane(selectedIngredientsList);
     selectedIngredientsScrollPane.setMaximumSize(new Dimension(300, 80));

     List<String> addedIngredientsList = new ArrayList<>();

     addIngredientButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             String selectedIngredient = (String) ingredientsComboBox.getSelectedItem();

             if (!selectedIngredient.isEmpty()) {
                 selectedIngredientsListModel.addElement(selectedIngredient);
                 ingredientsComboBox.removeItem(selectedIngredient);
                 addedIngredientsList.add(selectedIngredient);
             }
         }
     });


     JButton searchRecipe = new JButton("Szukaj przepisu");
     searchRecipe.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {

             try {
                 FilteringMethod.filteringMethod(addedIngredientsList, table);
             } catch (SQLException ex) {
                 throw new RuntimeException(ex);
             }

             selectedIngredientsListModel.clear();
             addedIngredientsList.clear();

         }
     });

     JButton clearFiltersButton = new JButton("Usuń filtry");
     clearFiltersButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             try {
                // MainMenu.menu();
                 ingredientsComboBox.removeAllItems();
                 PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(new Object());
                 MHdataModel statusBar = new MHdataModel(propertyChangeSupport);

                 statusBar.recipes = SelectMethod.getRecipesAll();
                 statusBar.fireTableDataChanged();

                  table.setModel(statusBar);
                  TableOptions.tableOptions(table);

                 for (int i = 0; i < products.size(); i++) {
                     originalComboBoxModel.addElement(products.get(i).getName());
                 }

                  ingredientsComboBox.setModel(originalComboBoxModel);
             } catch (IOException ex) {
                 throw new RuntimeException(ex);
             } catch (SQLException ex) {
                 throw new RuntimeException(ex);
             }
         }
     });

     JPanel selectPanel = new JPanel();
     selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
     selectPanel.add(label);
     panel.add(Box.createRigidArea(new Dimension(0, 30)));
     panel.add(selectPanel);

     JPanel selectPanel2 = new JPanel();
     selectPanel2.setLayout(new BoxLayout(selectPanel2, BoxLayout.X_AXIS));
     selectPanel2.add(label2);
     panel.add(selectPanel2);

     panel.add(Box.createRigidArea(new Dimension(0, 10)));
     panel.add(ingredientsComboBox);
     panel.add(Box.createRigidArea(new Dimension(0, 10)));


     JPanel selectPanel3 = new JPanel();
     selectPanel3.setLayout(new BoxLayout(selectPanel3, BoxLayout.X_AXIS));
     selectPanel3.add(addIngredientButton);
     panel.add(selectPanel3);
     panel.add(Box.createRigidArea(new Dimension(0, 10)));


     panel.add(selectedIngredientsScrollPane);


     JPanel selectPanel4 = new JPanel();
     selectPanel4.setLayout(new BoxLayout(selectPanel4, BoxLayout.X_AXIS));
     selectPanel4.add(searchRecipe);
     panel.add(selectPanel4);
     panel.add(Box.createRigidArea(new Dimension(0, 10)));

     JPanel selectPanel5 = new JPanel();
     selectPanel5.setLayout(new BoxLayout(selectPanel5, BoxLayout.X_AXIS));
     selectPanel5.add(clearFiltersButton);
     panel.add(selectPanel5);

 }


}
