package app;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class DeleteButton {
    public static void deleteButton(JFrame frame, JPanel panel, JTable table, EntityManagerFactory emf) {
        String[] deleteOptions = {"Przepis", "Produkt"};
        JComboBox<String> deleteOptionsComboBox = new JComboBox<>(deleteOptions);
        deleteOptionsComboBox.setMaximumSize(new Dimension(100, 25));
        JButton delete_button = new JButton("Usun");
        delete_button.setMaximumSize(new Dimension(150, 30));


        delete_button.setEnabled(false);


        deleteOptionsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDeleteButtonState(deleteOptionsComboBox, delete_button, table);
            }
        });


        ListSelectionModel rowSM = table.getSelectionModel();
        rowSM.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateDeleteButtonState(deleteOptionsComboBox, delete_button, table);
            }
        });


        delete_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) deleteOptionsComboBox.getSelectedItem();

                switch (selectedOption) {
                    case "Przepis":
                        frame.setEnabled(false);
                        int selectedRow = table.getSelectedRow();


                        if (selectedRow != -1 ) {

                            Object idValue = table.getValueAt(selectedRow, 0);
                            Object nameValue = table.getValueAt(selectedRow, 1);

                            try {
                                DeleteFrame.recipe(/*conn,*/ idValue, nameValue, table);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                        break;
                    case "Produkt":
                        frame.setEnabled(false);
                        try {
                            DeleteFrame.product(emf);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                }
            }
        });





        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
        selectPanel.add(delete_button);
        selectPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        selectPanel.add(deleteOptionsComboBox);
        panel.add(selectPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        updateDeleteButtonState(deleteOptionsComboBox, delete_button, table);
    }


    private static void updateDeleteButtonState(JComboBox<String> comboBox, JButton button, JTable table) {
        String selectedOption = (String) comboBox.getSelectedItem();
        int selectedRow = table.getSelectedRow();

        if ("Przepis".equals(selectedOption)) {
            button.setEnabled(selectedRow != -1);
        } else {
            button.setEnabled(true);
        }
    }
}



