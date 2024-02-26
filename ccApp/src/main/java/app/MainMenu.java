package app;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.sql.SQLException;

public class MainMenu {

    public static JFrame frame;
    public static void menu(EntityManagerFactory emf) throws IOException, SQLException {

        frame = new JFrame("ccApp");
        JPanel panel = new JPanel();

        PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(new Object());
        MHdataModel dataModel = new MHdataModel(propertyChangeSupport);

        panel.setLayout(new BorderLayout());


        dataModel.recipes = SelectMethod.getRecipesAll();
        dataModel.fireTableDataChanged();

        JTable table = new JTable(dataModel);

        JTableHeader header = table.getTableHeader();
        panel.add(header, BorderLayout.NORTH);


        JPanel statusBarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label1 = new JLabel("message");
        label1.setPreferredSize(new Dimension(200, 15));
        label1.setOpaque(true);
        JLabel label2 = new JLabel("error");
        label2.setPreferredSize(new Dimension(100, 15));
        label2.setOpaque(true);

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (table.getSelectedRowCount() > 0) {
                    table.clearSelection();
                    propertyChangeSupport.firePropertyChange("msg", "", "");
                }
            }
        });

        propertyChangeSupport.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("msg"))
                label1.setText(evt.getNewValue().toString());
            if (evt.getPropertyName().equals("err"))
                label2.setText(evt.getNewValue().toString());
        });



        Menu.menu(frame, table, emf);


        statusBarPanel.add(label1);
        statusBarPanel.add(label2);
        frame.add(statusBarPanel, BorderLayout.SOUTH);

        TableOptions.tableOptions(table);
        ListSelectionModel rowSM = table.getSelectionModel();

        rowSM.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting())
                return;

            ListSelectionModel lsm = (ListSelectionModel) e.getSource();
            if (lsm.isSelectionEmpty()) {
                propertyChangeSupport.firePropertyChange("msg", "", "No rows are selected.");
                propertyChangeSupport.firePropertyChange("err", "", "No rows are selected.");

            } else {
                int selectedRow = lsm.getMinSelectionIndex();
                if (dataModel.recipes.get(selectedRow) != null)
                    propertyChangeSupport.firePropertyChange("msg", "", dataModel.recipes.get(selectedRow).name);
                    propertyChangeSupport.firePropertyChange("err", "", dataModel.recipes.get(selectedRow).id);
            }

        });


        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        SelectButton.selectButton(frame, panel2, emf);
        InsertButton.insertButton(frame, panel2, table, emf);
        DeleteButton.deleteButton(frame, panel2, table, emf);
        UpdateButton.updateButton(frame, panel2, table);
        Filtering.filtering(panel2, table, emf);



        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel2, BorderLayout.EAST);
        PopupMenu.popupMenu(table, emf);
        frame.add(panel);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void close() {
        frame.setEnabled(true);
        frame.setVisible(true);
    }
}
