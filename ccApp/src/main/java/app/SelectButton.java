package app;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class SelectButton {

    public static void selectButton(JFrame frame, JPanel panel, EntityManagerFactory emf) {
        String[] selectOptions = {"Produkty", "Dni"};
        JComboBox<String> selectOptionsComboBox = new JComboBox<>(selectOptions);
        selectOptionsComboBox.setMaximumSize(new Dimension(100, 25));
        JButton select_button = new JButton("Wyświetl");
        select_button.setMaximumSize(new Dimension(150, 30));
        select_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) selectOptionsComboBox.getSelectedItem();

                switch (selectedOption) {
                    case "Produkty":
                        frame.setEnabled(false);
                        try {
                            SelectFrame.product(emf);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Dni":
                        JOptionPane.showMessageDialog(frame, "Unimplemented", "Info", JOptionPane.INFORMATION_MESSAGE);
                        break;
                }
            }
        });


        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
        selectPanel.add(select_button);
        selectPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        selectPanel.add(selectOptionsComboBox);
        panel.add(selectPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}
