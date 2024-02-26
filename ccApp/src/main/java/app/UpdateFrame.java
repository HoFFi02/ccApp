package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.sql.SQLException;


public class UpdateFrame {

    public static void recipe(Object idValue, Object nameValue, Object preparationValue, JTable table) throws IOException {
        JFrame frame = new JFrame("Edytuj Przepis");
       // JDialog dialog = new JDialog();
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

        if (nameValue != null && nameValue instanceof String) {
            nameField.setText((String) nameValue);
        }

        if (preparationValue != null && preparationValue instanceof String) {
            preparationArea.setText((String) preparationValue);
        }

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        JButton updateButton = new JButton("Edytuj Przepis");
        panel.add(updateButton, gbc);

        PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(new Object());
        MHdataModel statusBar = new MHdataModel(propertyChangeSupport);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String name = nameField.getText();
                    String preparation = preparationArea.getText();

                    UpdateMethod.recipe(preparation, name, (Integer)idValue);
                    statusBar.recipes = SelectMethod.getRecipesAll(/*conn*/);
                    table.setModel(statusBar);
                    TableOptions.tableOptions(table);
                    nameField.setText("");
                    preparationArea.setText("");


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
//        dialog.add(panel);
//        dialog.setTitle("Update record");
//        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//        dialog.setSize(500, 300);
//        dialog.setResizable(false);
//        dialog.setLocationRelativeTo(null);
//        //dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
//        dialog.setModalityType(Dialog.ModalityType.MODELESS);
//
//        dialog.setVisible(true);
    }
    public static void recipeNoValues(JTable table) throws IOException {
        JFrame frame = new JFrame("Edytuj Przepis");
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
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        JButton updateButton = new JButton("Edytuj Przepis");
        panel.add(updateButton, gbc);

        PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(new Object());
        MHdataModel statusBar = new MHdataModel(propertyChangeSupport);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String name = nameField.getText();
                    String preparation = preparationArea.getText();

                    UpdateMethod.recipeNoId(preparation, name);
                    statusBar.recipes = SelectMethod.getRecipesAll(/*conn*/);
                    //statusBarExample2.fireTableDataChanged();
                    table.setModel(statusBar);
                    TableOptions.tableOptions(table);
                    nameField.setText("");
                    preparationArea.setText("");


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
