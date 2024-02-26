package app;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
    MultiLineTableCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setEditable(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = (JTable) getParent();
                table.requestFocusInWindow();
                table.changeSelection(table.getEditingRow(), table.getEditingColumn(), false, false);
            }
        });
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        setText(value != null ? value.toString() : "");
        setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
        setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());


        FontMetrics metrics = getFontMetrics(getFont());
        int textWidth = metrics.stringWidth(getText());
        int textHeight = metrics.getHeight();
        int columnWidth = table.getColumnModel().getColumn(column).getWidth();


        int preferredHeight = calculatePreferredHeight(textWidth, textHeight, columnWidth);

        setPreferredSize(new Dimension(columnWidth, preferredHeight));
        table.setRowHeight(row, preferredHeight);

        return this;
    }

    private int calculatePreferredHeight(int textWidth, int textHeight, int columnWidth) {
        int lines = (textWidth / columnWidth) + 1;
        int additionalLines = 1;
        int preferredHeight = (lines + additionalLines) * textHeight;
        return preferredHeight;
    }
}
