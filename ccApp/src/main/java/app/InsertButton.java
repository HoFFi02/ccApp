package app;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class InsertButton {
    public static void insertButton(JFrame frame, JPanel panel, JTable table, EntityManagerFactory emf) {
        String[] insertOptions = {"Przepis", "Produkt"};
        JComboBox<String> insertOptionsComboBox = new JComboBox<>(insertOptions);
        insertOptionsComboBox.setMaximumSize(new Dimension(100, 25));
        JButton insert_button = new JButton("Dodaj");
        insert_button.setMaximumSize(new Dimension(150, 30));
        insert_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) insertOptionsComboBox.getSelectedItem();

                switch (selectedOption) {
                    case "Przepis":
                        frame.setEnabled(false);
                        try {
                            InsertFrame.recipe(table, emf);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Produkt":
                        frame.setEnabled(false);
                        try {
                            InsertFrame.product(emf);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                }
            }
        });





        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
        selectPanel.add(insert_button);
        selectPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        selectPanel.add(insertOptionsComboBox);
        panel.add(selectPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}
