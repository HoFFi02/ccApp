package app;


import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;


public class TableOptions {

    public static void tableOptions(JTable table){

        table.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        sorter.setSortsOnUpdates(true);
        table.getColumnModel().getColumn(2).setPreferredWidth(550);
        table.getColumnModel().getColumn(2).setCellRenderer(new MultiLineTableCellRenderer());
        table.getColumnModel().getColumn(1).setCellRenderer(new MultiLineTableCellRenderer());
        table.getColumnModel().getColumn(0).setCellRenderer(new MultiLineTableCellRenderer());
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        System.setProperty("file.encoding", "UTF-8");
        table.setFont(new Font("Arial", Font.PLAIN, 12));

    }
}
