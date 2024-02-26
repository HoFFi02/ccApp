package app;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class UpdateButton {
    public static void updateButton(JFrame frame, JPanel panel, JTable table) {

        JButton update_button = new JButton("Edytuj przepis");
        update_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.setEnabled(false);
                int selectedRow = table.getSelectedRow();
                int selectedColumn = table.getSelectedColumn();
                System.out.println(selectedColumn);

                if (selectedRow != -1) {

                    Object idValue = table.getValueAt(selectedRow, 0); //
                    Object nameValue = table.getValueAt(selectedRow, 1);
                    Object preparationValue = table.getValueAt(selectedRow, 2);

                    try {
                        UpdateFrame.recipe(idValue, nameValue, preparationValue, table);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        ListSelectionModel rowSM = table.getSelectionModel();
        rowSM.addListSelectionListener(e -> {

            boolean isRowSelected = !rowSM.isSelectionEmpty();
            update_button.setEnabled(isRowSelected);
        });

        update_button.setEnabled(!rowSM.isSelectionEmpty());

        update_button.setMaximumSize(new Dimension(150, 30));


        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
        selectPanel.add(update_button);
        panel.add(selectPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
}


