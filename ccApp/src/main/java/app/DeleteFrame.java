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

public class DeleteFrame {

    public static void product(EntityManagerFactory emf) throws IOException {
        JFrame frame = new JFrame("Usun produkt");
        frame.setSize(300, 200);
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
        nameField.setPreferredSize(new Dimension(200, 25));
        panel.add(nameField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        JButton deleteButton = new JButton("Usun produkt");
        panel.add(deleteButton, gbc);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();


                   // DeleteMethod.product(name);
                    DeleteMethod.productHibernate(name, emf);
                    nameField.setText("");

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


    public static void recipe(Object idValue, Object nameValue, JTable table) throws IOException {
        JFrame frame = new JFrame("Usun przepis");
        frame.setSize(300, 200); // Increase the size
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
        nameField.setPreferredSize(new Dimension(200, 25)); // Adjust size
        panel.add(nameField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        JButton deleteButton = new JButton("Usun przepis");
        panel.add(deleteButton, gbc);


        if (nameValue != null && nameValue instanceof String) {
            nameField.setText((String) nameValue);
        }
        PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(new Object());
        MHdataModel statusBar = new MHdataModel(propertyChangeSupport/*, conn*/);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();

                    DeleteMethod.recipe(name, (Integer) idValue);
                    statusBar.recipes = SelectMethod.getRecipesAll();
                    //statusBarExample2.fireTableDataChanged();
                    table.setModel(statusBar);
                    TableOptions.tableOptions(table);

                    nameField.setText("");
                    frame.dispose();
                } catch (SQLException | IOException ex) {
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


    public static void recipe2(JTable table) throws IOException {
        JFrame frame = new JFrame("Usun przepis");
        frame.setSize(300, 200); // Increase the size
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
        nameField.setPreferredSize(new Dimension(200, 25)); // Adjust size
        panel.add(nameField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        JButton deleteButton = new JButton("Usun przepis");
        panel.add(deleteButton, gbc);



        PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(new Object());
        MHdataModel statusBar = new MHdataModel(propertyChangeSupport);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();


                    DeleteMethod.recipeNoId(name);
                    statusBar.recipes = SelectMethod.getRecipesAll(/*conn*/);
                    table.setModel(statusBar);
                    TableOptions.tableOptions(table);

                    nameField.setText("");

                } catch (SQLException | IOException ex) {
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

}
