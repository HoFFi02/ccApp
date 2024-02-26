package app;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

class PopupMenu {
    public static void popupMenu(JTable table, EntityManagerFactory emf) {

        JPopupMenu popupmenu = new JPopupMenu("Edit");
        JMenuItem editItem = new JMenuItem("Edytuj");
        JMenuItem deleteItem = new JMenuItem("Usun");
        JMenuItem addItem = new JMenuItem("Dodaj");

        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                int selectedColumn = table.getSelectedColumn();
                System.out.println(selectedColumn);

                if (selectedRow != -1) {


                    try {
                        InsertFrame.recipe(table, emf);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });


        popupmenu.add(editItem);
        popupmenu.add(deleteItem);
        popupmenu.add(addItem);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {

                    int row = table.rowAtPoint(e.getPoint());
                    int col = table.columnAtPoint(e.getPoint());

                    table.changeSelection(row, col, false, false);

                    popupmenu.show(table, e.getX(), e.getY());
                }
            }
        });
    }
}
