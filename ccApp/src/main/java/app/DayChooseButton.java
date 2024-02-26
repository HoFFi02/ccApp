package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DayChooseButton {
    public static void dayChooseButton(JFrame frame, JPanel panel){

        Integer[] dayNumbers = new Integer[31];
        for (int i = 1; i <= 31; i++) {
            dayNumbers[i - 1] = i;
        }
        JComboBox<Integer> dayOptionsComboBox = new JComboBox<>(dayNumbers);
        dayOptionsComboBox.setMaximumSize(new Dimension(100, 25));
        JButton day_choose_button = new JButton("Wybierz dzien");


        day_choose_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Unimplemented", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        day_choose_button.setMaximumSize(new Dimension(150, 30));


        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new BoxLayout(selectPanel, BoxLayout.X_AXIS));
        selectPanel.add(day_choose_button);
        selectPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        selectPanel.add(dayOptionsComboBox);
        panel.add(selectPanel);
    }
}
