package app;

import javax.swing.table.AbstractTableModel;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MHdataModel extends AbstractTableModel{

    private static final long serialVersionUID = 1L;

    PropertyChangeSupport stanAplikacji;

    public MHdataModel(PropertyChangeSupport stanAplikacji) {
        super();
        this.stanAplikacji = stanAplikacji;
    }

    String column[] = { "ID", "Name", "Sposob_przygotowania"};
    ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    @Override
    public String getColumnName(int columnIndex) {
        return column[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return column.length;
    }

    @Override
    public int getRowCount() {
        return recipes.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //System.out.println("getValueAt " + rowIndex + " " + columnIndex);
     //   stanAplikacji.firePropertyChange("err", "", ""+rowIndex);

        if (recipes.get(rowIndex) == null) {
            System.out.println("getValueAt " + rowIndex + " " + columnIndex);
			stanAplikacji.firePropertyChange("err", "", ""+rowIndex);

            ArrayList<Recipe> chunk = null;
            try {
                chunk = SelectMethod.getRecipesAll();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            int i = 0;
            for (Recipe recipe : chunk) {
                recipes.set(rowIndex + i, recipe);
                i++;
            }
        }


        switch (columnIndex) {
            case 0:
                return recipes.get(rowIndex).id;
            case 1:
                return recipes.get(rowIndex).name;
            case 2:
                return recipes.get(rowIndex).preparation;
        }
        return "";
    }

    public boolean isCellEditable(int row, int col) {
        if (col < 1) {
            return false;
        } else {
            return true;
        }
    }
}
